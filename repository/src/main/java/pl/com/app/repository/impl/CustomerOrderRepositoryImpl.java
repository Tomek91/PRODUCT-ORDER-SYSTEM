package pl.com.app.repository.impl;

import pl.com.app.exceptions.EErrorsMessage;
import pl.com.app.exceptions.MyException;
import pl.com.app.repository.CustomerOrderRepository;
import pl.com.app.repository.generic.AbstractGenericRepository;
import pl.com.app.repository.generic.connection.DbConnection;
import pl.com.app.repository.model.CustomerOrder;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CustomerOrderRepositoryImpl extends AbstractGenericRepository<CustomerOrder> implements CustomerOrderRepository {

    @Override
    public List<CustomerOrder> findAllWithFetchGuarantee() {
        List<CustomerOrder> items = null;
        EntityManager entityManager = null;
        EntityTransaction tx = null;
        try (DbConnection connection = new DbConnection()) {
            entityManager = connection.getEntityManager();
            tx = connection.getEntityTransaction();
            tx.begin();
            items = entityManager
                    .createQuery("select distinct co from " + getEntityClass().getCanonicalName() +
                            " co join fetch co.product p " +
                            "left join fetch p.eGuarantees g", getEntityClass())
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
