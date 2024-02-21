package pl.nullpointerexception.hibernate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.nullpointerexception.hibernate.entity.Product;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;


public class App05OneToMany {

    private static Logger logger = LogManager.getLogger(App05OneToMany.class);
    private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("unit");

    public static void main(String[] args) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();

        List<Product> products = em.createQuery("select p from Product p").getResultList();
        for (Product product : products) {
            logger.info(product.getName());
            logger.info(product.getReviews());
        }
        em.getTransaction().commit();
        em.close();
    }
}
