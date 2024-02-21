package pl.nullpointerexception.hibernate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import pl.nullpointerexception.hibernate.entity.batch.BatchReview;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class App27Insert {
    private static Logger logger = LogManager.getLogger(App27Insert.class);
    private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("unit");

    public static void main(String[] args) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.unwrap(Session.class).setJdbcBatchSize(10);
        em.getTransaction().begin();

        Long lastId = em.createQuery("select max(r.id) from BatchReview r", Long.class).getSingleResult();

        for (long i = 1; i <= 25; i++) {
            if (i % 5 == 0) {
                em.flush();
                em.clear();
            }
            em.persist(new BatchReview(lastId + i, "Treść", 5, 1L));
        }

        em.getTransaction().commit();
        em.close();
    }
}
