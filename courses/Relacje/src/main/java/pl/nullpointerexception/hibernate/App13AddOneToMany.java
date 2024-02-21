package pl.nullpointerexception.hibernate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.nullpointerexception.hibernate.entity.Product;
import pl.nullpointerexception.hibernate.entity.Review;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class App13AddOneToMany {
    private static Logger logger = LogManager.getLogger(App13AddOneToMany.class);
    private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("unit");

    public static void main(String[] args) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();

        Product product = em.find(Product.class, 5L);
//        Review review = new Review();
//        review.setContent("Nowa opinia");
//        review.setRating(5);
//        //review.setProduct(product);
//        product.addReview(review);
        Review review = em.find(Review.class, 12L);
        product.addReview(review);

        em.getTransaction().commit();
        em.close();
    }
}
