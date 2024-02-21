package pl.maciek.ecommerce.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import pl.maciek.ecommerce.entity.Customer;


public interface CustomerRepository extends JpaRepository<Customer,Long> {

    Customer findByEmail(String theEmail);


}
