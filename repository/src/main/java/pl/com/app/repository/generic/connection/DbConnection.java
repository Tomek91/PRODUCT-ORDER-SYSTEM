package pl.com.app.repository.generic.connection;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class DbConnection implements AutoCloseable {

    private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("HBN");
    private EntityManager entityManager;
    private EntityTransaction entityTransaction;

    public DbConnection() {
        entityManager = entityManagerFactory.createEntityManager();
        entityTransaction = entityManager.getTransaction();
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public EntityTransaction getEntityTransaction() {
        return entityTransaction;
    }

    @Override
    public void close() {
        if (entityManager != null) {
            entityManager.close();
        }
    }

    public static void closeEntityManagerFactory() {
        if (entityManagerFactory != null) {
            entityManagerFactory.close();
        }
    }
}
