package pl.nullpointerexception.hibernate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.nullpointerexception.hibernate.entity.Attribute;
import pl.nullpointerexception.hibernate.entity.Product;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class App10ManyToMany {
    private static Logger logger = LogManager.getLogger(App10ManyToMany.class);
    private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("unit");

    public static void main(String[] args) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();

        Product product = em.find(Product.class, 3L);
        logger.info(product);
        logger.info(product.getAttributes());

        logger.info("===");

        Attribute attribute = em.find(Attribute.class, 3L);
        logger.info(attribute);
        logger.info(attribute.getProducts());

        em.getTransaction().commit();
        em.close();
    }
}
