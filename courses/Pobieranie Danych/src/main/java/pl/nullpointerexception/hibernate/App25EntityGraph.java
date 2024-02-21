package pl.nullpointerexception.hibernate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.nullpointerexception.hibernate.entity.Order;
import pl.nullpointerexception.hibernate.entity.OrderRow;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Subgraph;
import java.util.List;

public class App25EntityGraph {
    private static Logger logger = LogManager.getLogger(App25EntityGraph.class);
    private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("unit");

    public static void main(String[] args) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();

//        EntityGraph entityGraph = em.getEntityGraph("order-rows");
//
//        Map<String, Object> map = new HashMap<>();
//        map.put("javax.persistence.loadgraph", entityGraph);
//
//        Order order = em.find(Order.class, 1L, map);
//        logger.info(order);
//        for (OrderRow orderRow : order.getOrderRows()) {
//            logger.info(orderRow);
//            logger.info(orderRow.getProduct());
//        }

        EntityGraph entityGraph = em.createEntityGraph(Order.class);
        entityGraph.addAttributeNodes("orderRows");
        //entityGraph.addAttributeNodes("customer");
        Subgraph<OrderRow> orderRows = entityGraph.addSubgraph("orderRows");
        orderRows.addAttributeNodes("product");

        ///
//        Map<String, Object> map = new HashMap<>();
//        map.put("javax.persistence.loadgraph", entityGraph);

        //Order order = em.find(Order.class, 1L, map);

        List<Order> orders = em.createQuery(
                "select o from Order o" +
                        " left join fetch o.customer",
                Order.class
        )
                .setHint("javax.persistence.loadgraph", entityGraph)
                .getResultList();
        for (Order order : orders) {
            logger.info(order);
            for (OrderRow orderRow : order.getOrderRows()) {
                logger.info(orderRow);
                logger.info(orderRow.getProduct());
            }
        }

        em.getTransaction().commit();
        em.close();
    }
}
