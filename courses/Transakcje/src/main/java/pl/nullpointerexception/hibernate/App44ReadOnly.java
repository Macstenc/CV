package pl.nullpointerexception.hibernate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.nullpointerexception.hibernate.entity.Customer;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDateTime;

public class App44ReadOnly {
    private static Logger logger = LogManager.getLogger(App44ReadOnly.class);
    private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("unit");

    public static void main(String[] args) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();

        Customer customer = em.createQuery("select c from Customer c where c.id = :id", Customer.class)
                .setParameter("id", 1L)
                //.setHint(QueryHints.HINT_READONLY, true)
                .getSingleResult();

        customer.setUpdated(LocalDateTime.now());
        em.merge(customer);
        logger.info(customer);

        em.getTransaction().commit();
        em.close();
    }
}
