package pl.kul.Sklep.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.kul.Sklep.Entity.Order;

import java.util.List;

public interface OrderRepository extends MongoRepository<Order, String> {
    List<Order> findByUserEmail(String userName);

}
