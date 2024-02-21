package pl.nullpointerexception.hibernate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.nullpointerexception.hibernate.entity.Category;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class App09OneToOneBidirectional {
    private static Logger logger = LogManager.getLogger(App09OneToOneBidirectional.class);
    private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("unit");

    public static void main(String[] args) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();

        Category category = em.find(Category.class, 1L);
        logger.info(category);
        logger.info(category.getProduct());

        em.getTransaction().commit();
        em.close();
    }
}
