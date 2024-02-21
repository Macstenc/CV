package pl.nullpointerexception.hibernate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.nullpointerexception.hibernate.entity.Category;
import pl.nullpointerexception.hibernate.entity.Product;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class App11AddOneToOne {
    private static Logger logger = LogManager.getLogger(App11AddOneToOne.class);
    private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("unit");

    public static void main(String[] args) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();

        Product product = em.find(Product.class, 3L);
        Category category = new Category();
        category.setName("Nowa kategoria");
        category.setDescription("Opis nowej kategorii");
        em.persist(category);
        product.setCategory(category);

        em.getTransaction().commit();
        em.close();
    }
}
