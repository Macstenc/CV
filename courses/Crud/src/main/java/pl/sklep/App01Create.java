package pl.sklep;

import pl.sklep.entity.Product;
import pl.sklep.entity.ProductType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class App01Create {

    private static Logger logger = LogManager.getLogger(App.class);
    private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("unit");

    public static void main(String[] args) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();

        Product product = new Product();
        product.setName("Produkt 01");
        product.setDescription("Opis produktu 01");
        product.setCreated(LocalDateTime.now());
        product.setUpdated(LocalDateTime.now());
        product.setPrice(new BigDecimal("19.99"));
        product.setProductType(ProductType.REAL);

        em.persist(product);
        logger.info(product);
        em.getTransaction().commit();
        em.close();
    }
}
