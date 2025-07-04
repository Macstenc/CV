package pl.kul.Sklep.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.kul.Sklep.Entity.User;

public interface UserRepository extends MongoRepository<User,String> {
    User findByUsername(String username);
    User findByEmail(String email);
    boolean existsByEmail(String email);

}
