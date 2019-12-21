package pl.com.app.repository.impl;


import pl.com.app.exceptions.EErrorsMessage;
import pl.com.app.exceptions.MyException;
import pl.com.app.repository.StockRepository;
import pl.com.app.repository.generic.AbstractGenericRepository;
import pl.com.app.repository.generic.connection.DbConnection;
import pl.com.app.repository.model.Stock;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

public class StockRepositoryImpl extends AbstractGenericRepository<Stock> implements StockRepository {

    @Override
    public Optional<Stock> findByProductIdAndShopId(Long productId, Long shopId) {
        Optional<Stock> opt = null;
        EntityManager entityManager = null;
        EntityTransaction tx = null;
        try (DbConnection connection = new DbConnection()) {
            entityManager = connection.getEntityManager();
            tx = connection.getEntityTransaction();
            tx.begin();
            opt = entityManager
                    .createQuery("select s from Stock s where s.product.id = :productId and s.shop.id = :shopId", Stock.class)
                    .setParameter("productId", productId)
                    .setParameter("shopId", shopId)
                    .getResultList()
                    .stream()
                    .findFirst();
            tx.commit();
            getLogger().info(getEntityClass().getCanonicalName() + " findedBy ProductId and ShopId");
        } catch (Exception e) {
            getLogger().error(getEntityClass().getCanonicalName() + ": " + e.getMessage());
            getLogger().error(getEntityClass().getCanonicalName() + ": " + Arrays.stream(e.getStackTrace()).map(StackTraceElement::toString).collect(Collectors.joining("\n\t")));
            if (tx != null) {
                try {
                    tx.rollback();
                } catch (Exception ee) {
                    throw new MyException(String.join(";",
                            getEntityClass().getCanonicalName(),
                            EErrorsMessage.FIND_BY_NAME_ROLLBACK_EXCEPTION.toString()));
                }
            }
            throw new MyException(String.join(";",
                    getEntityClass().getCanonicalName(),
                    EErrorsMessage.FIND_BY_PRODUCTID_AND_SHOPID_EXCEPTION.toString()));
        }
        getLogger().info(getEntityClass().getCanonicalName() + " findedBy Name - session closed");
        return opt;
    }
}
