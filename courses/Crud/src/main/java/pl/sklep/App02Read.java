package pl.sklep;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.sklep.entity.Product;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class App02Read {

    private static Logger logger = LogManager.getLogger(App.class);
    private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("unit");

    public static void main(String[] args) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();

        Product product = em.find(Product.class, 1L);
        logger.info(product);
        em.getTransaction().commit();
        em.close();
    }
}
