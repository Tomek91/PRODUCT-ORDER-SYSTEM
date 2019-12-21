package pl.com.app.repository.impl;


import pl.com.app.exceptions.EErrorsMessage;
import pl.com.app.exceptions.MyException;
import pl.com.app.repository.CountryRepository;
import pl.com.app.repository.generic.AbstractGenericRepository;
import pl.com.app.repository.generic.connection.DbConnection;
import pl.com.app.repository.model.Country;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

public class CountryRepositoryImpl extends AbstractGenericRepository<Country> implements CountryRepository {

    @Override
    public Optional<Country> findByName(String name) {
        Optional<Country> opt = null;
        EntityManager entityManager = null;
        EntityTransaction tx = null;
        try (DbConnection connection = new DbConnection()) {
            entityManager = connection.getEntityManager();
            tx = connection.getEntityTransaction();
            tx.begin();
            opt = entityManager
                    .createQuery("select c from Country c where c.name = :name", Country.class)
                    .setParameter("name", name)
                    .getResultList()
                    .stream()
                    .findFirst();
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
                            EErrorsMessage.FIND_BY_NAME_ROLLBACK_EXCEPTION.toString()));
                }
            }
            throw new MyException(String.join(";",
                    getEntityClass().getCanonicalName(),
                    EErrorsMessage.FIND_BY_NAME_EXCEPTION.toString()));
        }
        getLogger().info(getEntityClass().getCanonicalName() + " findedBy Name - session closed");
        return opt;
    }
}
