package pl.nullpointerexception.hibernate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class App30UpdateQuerying {
    private static Logger logger = LogManager.getLogger(App30UpdateQuerying.class);
    private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("unit");

    public static void main(String[] args) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();

        int updated = em.createQuery("update Review r SET rating=:rating" +
                " where r.product.id=:id")
                .setParameter("rating", 11)
                .setParameter("id", 1L)
                .executeUpdate();
        logger.info("zakutalizowano: " + updated);

        em.getTransaction().commit();
        em.close();
    }
}
