package pl.nullpointerexception.hibernate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import pl.nullpointerexception.hibernate.entity.batch.BatchReview;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class App28UpdateAndPaging {
    private static Logger logger = LogManager.getLogger(App28UpdateAndPaging.class);
    private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("unit");

    public static void main(String[] args) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();

        Long count = em.createQuery("select count(r) from BatchReview r", Long.class).getSingleResult();
        int batchSize = 10;
        em.unwrap(Session.class).setJdbcBatchSize(batchSize);
        for (int i = 0; i < count; i = i + batchSize) {
            List<BatchReview> review = em.createQuery(
                    "select r from BatchReview r",
                    BatchReview.class
            )
                    .setFirstResult(i)
                    .setMaxResults(batchSize)
                    .getResultList();
            logger.info(review);
            for (BatchReview batchReview : review) {
                batchReview.setContent("Nowa treść !!!!!!!!!");
                batchReview.setRating(15);
            }
            em.flush();
            em.clear();
        }

        em.getTransaction().commit();
        em.close();
    }
}
