package pl.com.app.repository.generic;

import org.apache.log4j.Logger;
import pl.com.app.exceptions.EErrorsMessage;
import pl.com.app.exceptions.MyException;
import pl.com.app.repository.generic.connection.DbConnection;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class AbstractGenericRepository<T> implements GenericRepository<T> {

    private final Class<T> entityClass = (Class<T>) ((ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    private static final Logger logger = Logger.getLogger(AbstractGenericRepository.class);

    protected Logger getLogger() {
        return logger;
    }

    protected Class<T> getEntityClass() {
        return entityClass;
    }

    @Override
    public void addOrUpdate(T item) {
        EntityManager entityManager = null;
        EntityTransaction tx = null;
        try (DbConnection connection = new DbConnection()) {
            entityManager = connection.getEntityManager();
            tx = connection.getEntityTransaction();
            tx.begin();
            entityManager.merge(item);
            tx.commit();
            logger.info(entityClass.getCanonicalName() + " inserted or updated");
        } catch (Exception e) {
            logger.error(entityClass.getCanonicalName() + ": " + e.getMessage());
            logger.error(entityClass.getCanonicalName() + ": " + Arrays.stream(e.getStackTrace()).map(StackTraceElement::toString).collect(Collectors.joining("\n\t")));
            if (tx != null) {
                try {
                    tx.rollback();
                } catch (Exception ee) {
                    throw new MyException(String.join(";",
                            entityClass.getCanonicalName(),
                            EErrorsMessage.ADD_OR_UPDATE_ROLLBACK_EXCEPTION.toString()));
                }
            }
            throw new MyException(String.join(";",
                    entityClass.getCanonicalName(),
                    EErrorsMessage.ADD_OR_UPDATE_EXCEPTION.toString()));
        }
        logger.info(entityClass.getCanonicalName() + " inserted or updated - session closed");
    }

    @Override
    public void addAll(List<T> items) {
        EntityManager entityManager = null;
        EntityTransaction tx = null;
        try (DbConnection connection = new DbConnection()) {
            entityManager = connection.getEntityManager();
            tx = connection.getEntityTransaction();
            tx.begin();
            for (T item : items) {
                entityManager.merge(item);
            }
            tx.commit();
            logger.info(entityClass.getCanonicalName() + " inserted all");
        } catch (Exception e) {
            logger.error(entityClass.getCanonicalName() + ": " + e.getMessage());
            logger.error(entityClass.getCanonicalName() + ": " + Arrays.stream(e.getStackTrace()).map(StackTraceElement::toString).collect(Collectors.joining("\n\t")));
            if (tx != null) {
                try {
                    tx.rollback();
                } catch (Exception ee) {
                    throw new MyException(String.join(";",
                            entityClass.getCanonicalName(),
                            EErrorsMessage.ADD_ALL_ROLLBACK_EXCEPTION.toString()));
                }
            }
            throw new MyException(String.join(";",
                    entityClass.getCanonicalName(),
                    EErrorsMessage.ADD_ALL_EXCEPTION.toString()));
        }
        logger.info(entityClass.getCanonicalName() + " inserted all - session closed");
    }

    @Override
    public void delete(Long id) {
        EntityManager entityManager = null;
        EntityTransaction tx = null;
        try (DbConnection connection = new DbConnection()) {
            entityManager = connection.getEntityManager();
            tx = connection.getEntityTransaction();
            tx.begin();
            T item = entityManager.find(entityClass, id);
            entityManager.remove(item);
            tx.commit();
            logger.info(entityClass.getCanonicalName() + " deleted");
        } catch (Exception e) {
            logger.error(entityClass.getCanonicalName() + ": " + e.getMessage());
            logger.error(entityClass.getCanonicalName() + ": " + Arrays.stream(e.getStackTrace()).map(StackTraceElement::toString).collect(Collectors.joining("\n\t")));
            if (tx != null) {
                try {
                    tx.rollback();
                } catch (Exception ee) {
                    throw new MyException(String.join(";",
                            entityClass.getCanonicalName(),
                            EErrorsMessage.DELETE_ROLLBACK_EXCEPTION.toString()));
                }
            }
            throw new MyException(String.join(";",
                    entityClass.getCanonicalName(),
                    EErrorsMessage.DELETE_EXCEPTION.toString()));
        }
        logger.info(entityClass.getCanonicalName() + " deleted - session closed");
    }

    @Override
    public void deleteAll() {
        List<T> items = null;
        EntityManager entityManager = null;
        EntityTransaction tx = null;
        try (DbConnection connection = new DbConnection()) {
            entityManager = connection.getEntityManager();
            tx = connection.getEntityTransaction();
            tx.begin();
            items = entityManager
                    .createQuery("select t from " + entityClass.getSimpleName() + " t", entityClass)
                    .getResultList();
            for (T item : items) {
                entityManager.remove(item);
            }
            tx.commit();
            logger.info(entityClass.getCanonicalName() + " deletedALL - session closed");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(entityClass.getCanonicalName() + ": " + e.getMessage());
            logger.error(entityClass.getCanonicalName() + ": " + Arrays.stream(e.getStackTrace()).map(StackTraceElement::toString).collect(Collectors.joining("\n\t")));
            if (tx != null) {
                try {
                    tx.rollback();
                } catch (Exception ee) {
                    throw new MyException(String.join(";",
                            entityClass.getCanonicalName(),
                            EErrorsMessage.DELETE_ALL_ROLLBACK_EXCEPTION.toString()));
                }
            }
            throw new MyException(String.join(";",
                    entityClass.getCanonicalName(),
                    EErrorsMessage.DELETE_ALL_EXCEPTION.toString()));
        }
        logger.info(entityClass.getCanonicalName() + " deletedALL - session closed");
    }

    @Override
    public Optional<T> findById(Long id) {
        Optional<T> op = Optional.empty();
        EntityManager entityManager = null;
        EntityTransaction tx = null;
        try (DbConnection connection = new DbConnection()) {
            entityManager = connection.getEntityManager();
            tx = connection.getEntityTransaction();
            tx.begin();
            op = Optional.of(entityManager.find(entityClass, id));
            tx.commit();
            logger.info(entityClass.getCanonicalName() + " findedById");
        } catch (Exception e) {
            logger.error(entityClass.getCanonicalName() + ": " + e.getMessage());
            logger.error(entityClass.getCanonicalName() + ": " + Arrays.stream(e.getStackTrace()).map(StackTraceElement::toString).collect(Collectors.joining("\n\t")));
            if (tx != null) {
                try {
                    tx.rollback();
                } catch (Exception ee) {
                    throw new MyException(String.join(";",
                            entityClass.getCanonicalName(),
                            EErrorsMessage.FIND_BY_ID_ROLLBACK_EXCEPTION.toString()));
                }
            }
            throw new MyException(String.join(";",
                    entityClass.getCanonicalName(),
                    EErrorsMessage.FIND_BY_ID_EXCEPTION.toString()));
        }
        logger.info(entityClass.getCanonicalName() + " findedById - session closed");
        return op;
    }

    @Override
    public List<T> findAll() {
        List<T> items = null;
        EntityManager entityManager = null;
        EntityTransaction tx = null;
        try (DbConnection connection = new DbConnection()) {
            entityManager = connection.getEntityManager();
            tx = connection.getEntityTransaction();
            tx.begin();
            items = entityManager
                    .createQuery("select t from " + entityClass.getCanonicalName() + " t", entityClass)
                    .getResultList();
            tx.commit();
            logger.info(entityClass.getCanonicalName() + " findedAll");
        } catch (Exception e) {
            logger.error(entityClass.getCanonicalName() + ": " + e.getMessage());
            logger.error(entityClass.getCanonicalName() + ": " + Arrays.stream(e.getStackTrace()).map(StackTraceElement::toString).collect(Collectors.joining("\n\t")));
            if (tx != null) {
                try {
                    tx.rollback();
                } catch (Exception ee) {
                    throw new MyException(String.join(";",
                            entityClass.getCanonicalName(),
                            EErrorsMessage.FIND_ALL_ROLLBACK_EXCEPTION.toString()));
                }
            }
            throw new MyException(String.join(";",
                    entityClass.getCanonicalName(),
                    EErrorsMessage.FIND_ALL_EXCEPTION.toString()));
        }
        logger.info(entityClass.getCanonicalName() + " findedAll - session closed");
        return items;
    }
}
