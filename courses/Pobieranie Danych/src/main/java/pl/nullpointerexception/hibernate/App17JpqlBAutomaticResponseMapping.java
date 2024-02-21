package pl.nullpointerexception.hibernate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.nullpointerexception.hibernate.entity.ProductInCategoryCounterDto;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class App17JpqlBAutomaticResponseMapping {
    private static Logger logger = LogManager.getLogger(App17JpqlBAutomaticResponseMapping.class);
    private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("unit");

    public static void main(String[] args) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();

        Query query = em.createQuery(
                "select new pl.nullpointerexception.hibernate.entity.ProductInCategoryCounterDto(p.category.id, COUNT(p)) " +
                        "from Product p group by p.category"
        );

        List<ProductInCategoryCounterDto> resultList = query.getResultList();
        for (ProductInCategoryCounterDto dto : resultList) {
            logger.info(dto.getCategoryId());
            logger.info(dto.getProductInCategoryCounter());
        }

        em.getTransaction().commit();
        em.close();
    }
}
