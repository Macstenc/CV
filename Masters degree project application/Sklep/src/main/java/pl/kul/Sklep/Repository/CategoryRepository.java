package pl.kul.Sklep.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.kul.Sklep.Entity.Category;

@Repository
public interface CategoryRepository extends MongoRepository<Category, String> {
    boolean existsByName(String name);

    Category findByName(String categoryName);
}
