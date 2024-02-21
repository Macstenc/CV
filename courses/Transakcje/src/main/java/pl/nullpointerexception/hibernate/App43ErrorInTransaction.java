package pl.nullpointerexception.hibernate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.nullpointerexception.hibernate.entity.Customer;
import pl.nullpointerexception.hibernate.entity.Product;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class App43ErrorInTransaction {
    private static Logger logger = LogManager.getLogger(App43ErrorInTransaction.class);
    private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("unit");

    public static void main(String[] args) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();

        try {
            Customer customer = em.createQuery("select c from Customer c where c.id = :id", Customer.class)
                    .setParameter("id", 99L)
                    .getSingleResult();
            logger.info(customer);
            Product product = em.createQuery("select p from Product p where p.id = :id", Product.class)
                    .setParameter("id", 1L)
                    .getSingleResult();
            logger.info(product);
            em.getTransaction().commit();
        } catch (Exception e) {
            logger.info(em.getTransaction().isActive());
            logger.info(em.getTransaction().getRollbackOnly());
            if(em.getTransaction().isActive() || em.getTransaction().getRollbackOnly()) {
                em.getTransaction().rollback();
                logger.error("Rollback transakcji");
            }
            logger.error(e, e);
        }

        em.close();
    }
}
