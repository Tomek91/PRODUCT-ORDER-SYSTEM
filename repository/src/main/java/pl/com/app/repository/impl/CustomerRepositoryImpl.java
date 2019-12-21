package pl.com.app.repository.impl;

import pl.com.app.exceptions.EErrorsMessage;
import pl.com.app.exceptions.MyException;
import pl.com.app.repository.CustomerRepository;
import pl.com.app.repository.generic.AbstractGenericRepository;
import pl.com.app.repository.generic.connection.DbConnection;
import pl.com.app.repository.model.Customer;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CustomerRepositoryImpl extends AbstractGenericRepository<Customer> implements CustomerRepository {

    @Override
    public List<Customer> findByNameSurname(String name, String surname) {
        List<Customer> customers = null;
        EntityManager entityManager = null;
        EntityTransaction tx = null;
        try (DbConnection connection = new DbConnection()) {
            entityManager = connection.getEntityManager();
            tx = connection.getEntityTransaction();
            tx.begin();
            customers = entityManager
                    .createQuery("select c from Customer c where c.name = :name and c.surname = :surname", Customer.class)
                    .setParameter("name", name)
                    .setParameter("surname", surname)
                    .getResultList();
            tx.commit();
            getLogger().info(getEntityClass().getCanonicalName() + " findedByName");
        } catch (Exception e) {
            getLogger().error(getEntityClass().getCanonicalName() + ": " + e.getMessage());
            getLogger().error(getEntityClass().getCanonicalName() + ": " + Arrays.stream(e.getStackTrace()).map(StackTraceElement::toString).collect(Collectors.joining("\n\t")));
            if (tx != null) {
                try {
                    tx.rollback();
                } catch (Exception ee) {
                    throw new MyException(String.join(";",
                            getEntityClass().getCanonicalName(),
                            EErrorsMessage.FIND_BY_NAME_SURNAME_ROLLBACK_EXCEPTION.toString()));
                }
            }
            throw new MyException(String.join(";",
                    getEntityClass().getCanonicalName(),
                    EErrorsMessage.FIND_BY_NAME_SURNAME_EXCEPTION.toString()));
        }
        getLogger().info(getEntityClass().getCanonicalName() + " findedBy Name Surname - session closed");
        return customers;
    }
}
