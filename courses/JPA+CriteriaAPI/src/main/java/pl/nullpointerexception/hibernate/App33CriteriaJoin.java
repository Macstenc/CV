package pl.nullpointerexception.hibernate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.nullpointerexception.hibernate.entity.Customer;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.List;

public class App33CriteriaJoin {
    private static Logger logger = LogManager.getLogger(App33CriteriaJoin.class);
    private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("unit");

    public static void main(String[] args) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Customer> criteriaQuery = criteriaBuilder.createQuery(Customer.class);
        Root<Customer> customer = criteriaQuery.from(Customer.class);
        Join<Object, Object> orders = (Join<Object, Object>) customer.fetch("orders", JoinType.INNER);
        orders.fetch("orderRows")
                .fetch("product");
        ParameterExpression<Long> id = criteriaBuilder.parameter(Long.class);
        ParameterExpression<BigDecimal> total = criteriaBuilder.parameter(BigDecimal.class);
        criteriaQuery.select(customer).distinct(true)
                // where c.id=:id and o.total > 50
                .where(
                        criteriaBuilder.and(
                            criteriaBuilder.equal(customer.get("id"), id),
                                criteriaBuilder.greaterThan(orders.get("total"), total)
                        )
                );

        TypedQuery<Customer> query = em.createQuery(criteriaQuery);
        query.setParameter(id, 1L);
        query.setParameter(total, new BigDecimal("50.00"));
        List<Customer> results = query.getResultList();
        for (Customer result : results) {
            logger.info(result);
            result.getOrders().forEach(order -> {
                logger.info(order);
                order.getOrderRows().forEach(orderRow -> {
                    logger.info(orderRow);
                    logger.info(orderRow.getProduct());
                });
            });
        }

        em.getTransaction().commit();
        em.close();
    }
}
