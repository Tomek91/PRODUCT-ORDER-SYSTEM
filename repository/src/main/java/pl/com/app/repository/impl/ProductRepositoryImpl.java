package pl.com.app.repository.impl;


import pl.com.app.exceptions.EErrorsMessage;
import pl.com.app.exceptions.MyException;
import pl.com.app.repository.ProductRepository;
import pl.com.app.repository.generic.AbstractGenericRepository;
import pl.com.app.repository.generic.connection.DbConnection;
import pl.com.app.repository.model.Product;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ProductRepositoryImpl extends AbstractGenericRepository<Product> implements ProductRepository {

    @Override
    public List<Product> findAll() {
        List<Product> items = null;
        EntityManager entityManager = null;
        EntityTransaction tx = null;
        try (DbConnection connection = new DbConnection()) {
            entityManager = connection.getEntityManager();
            tx = connection.getEntityTransaction();
            tx.begin();
            items = entityManager
                    .createQuery("select distinct p from " + getEntityClass().getCanonicalName() +
                            " p left join fetch p.stock s" +
                            "   left join fetch p.customerOrders co", getEntityClass())
                    .getResultList();
            tx.commit();
            getLogger().info(getEntityClass().getCanonicalName() + " findedAll");
        } catch (Exception e) {
            getLogger().error(getEntityClass().getCanonicalName() + ": " + e.getMessage());
            getLogger().error(getEntityClass().getCanonicalName() + ": " + Arrays.stream(e.getStackTrace()).map(StackTraceElement::toString).collect(Collectors.joining("\n\t")));
            if (tx != null) {
                try {
                    tx.rollback();
                } catch (Exception ee) {
                    throw new MyException(String.join(";",
                            getEntityClass().getCanonicalName(),
                            EErrorsMessage.FIND_ALL_ROLLBACK_EXCEPTION.toString()));
                }
            }
            throw new MyException(String.join(";",
                    getEntityClass().getCanonicalName(),
                    EErrorsMessage.FIND_ALL_EXCEPTION.toString()));
        }
        getLogger().info(getEntityClass().getCanonicalName() + " findedAll - session closed");
        return items;
    }
}
