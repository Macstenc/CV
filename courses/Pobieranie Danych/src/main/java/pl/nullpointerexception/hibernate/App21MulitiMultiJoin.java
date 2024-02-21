package pl.nullpointerexception.hibernate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class App21MulitiMultiJoin {
    private static Logger logger = LogManager.getLogger(App21MulitiMultiJoin.class);
    private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("unit");

    public static void main(String[] args) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();

        /*
        select cu.id, cu.lastname customer, ca.name category, SUM(orw.price) total from customer cu
        inner join `order` o on cu.id=o.customer_id
        inner join order_row orw on o.id=orw.order_id
        inner join product p on orw.product_id=p.id
        inner join category ca on p.category_id=ca.id
        group by ca.id, cu.id
        having total > 50
        order by total DESC;
         */
        Query query = em.createQuery(
                "select distinct c.id, c.lastname as customer, ca.name as category, SUM(orw.price) as total " +
                        "from Customer c" +
                        " inner join c.orders o" +
                        " inner join o.orderRows orw" +
                        " inner join orw.product p" +
                        " inner join p.category ca" +
                        " group by ca, c" +
                        " having SUM(orw.price) > 50" +
                        " order by total desc"
        );

        List<Object[]> resultList = query.getResultList();
        for (Object[] row : resultList) {
            logger.info(row[0] + ", \t" + row[1] + ", \t" + row[2] + ", \t" + row[3]);
        }

        em.getTransaction().commit();
        em.close();
    }
}
