package pl.nullpointerexception.hibernate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.nullpointerexception.hibernate.entity.Order;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class App26NplusOne {
    private static Logger logger = LogManager.getLogger(App26NplusOne.class);
    private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("unit");

    public static void main(String[] args) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();

//        List<Order> products = em.createQuery(
//                "select distinct o from Order o" +
//                        " inner join fetch o.orderRows",
//                Order.class
//        ).getResultList();
//
//        logger.info("Ilość zamówień: " + products.size());
//        for (Order order : products) {
//            logger.info(order.getId());
//            logger.info(order.getOrderRows());
//        }

//        EntityGraph entityGraph = em.getEntityGraph("order-and-rows");
//        List<Order> products = em.createQuery(
//                "select o from Order o",
//                Order.class
//        )
//                .setHint("javax.persistence.fetchgraph", entityGraph)
//                .getResultList();
//
//        logger.info("Ilość zamówień: " + products.size());
//        for (Order order : products) {
//            logger.info(order.getId());
//            logger.info(order.getOrderRows());
//        }

        List<Order> products = em.createQuery(
                "select o from Order o",
                Order.class
        ).getResultList();

        logger.info("Ilość zamówień: " + products.size());
        for (Order order : products) {
            logger.info(order.getId());
            logger.info(order.getOrderRows());
        }

        em.getTransaction().commit();
        em.close();
    }
}
