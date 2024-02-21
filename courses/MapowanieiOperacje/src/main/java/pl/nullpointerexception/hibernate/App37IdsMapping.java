package pl.nullpointerexception.hibernate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.nullpointerexception.hibernate.entity.Customer;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class App37IdsMapping {
    private static Logger logger = LogManager.getLogger(App37IdsMapping.class);
    private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("unit");

    public static void main(String[] args) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();

        Customer customer = em.find(Customer.class, 1L);
//        CustomerDetails customerDetails = new CustomerDetails();
//        customerDetails.setBirthPlace("Warszawa");
//        customerDetails.setBirthDay(LocalDate.of(2000, 10, 22));
//        customerDetails.setFatherName("Jan");
//        customerDetails.setMotherName("Janina");
//        customerDetails.setPesel("11111111111");
//        customerDetails.setCustomer(customer);
//        customer.setCustomerDetails(customerDetails);
//        em.persist(customer);

        logger.info(customer);
        logger.info(customer.getCustomerDetails());

        em.getTransaction().commit();
        em.close();
    }
}
