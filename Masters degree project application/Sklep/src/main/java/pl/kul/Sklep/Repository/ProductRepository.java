package pl.kul.Sklep.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.kul.Sklep.Entity.Product;

import java.util.List;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
    List<Product> findByCategory(String category, Sort sort);

    List<Product> findByCategory(String category);
    List<Product> findByCategoryAndPrice(String category,double price);
    List<Product> findByCategoryOrPrice(String category,double price);
    List<Product> findByCategoryLike(String category);
    List<Product> findByNameStartingWith(String name);
    List<Product> findByIsRecommendedTrue(Pageable pageable);
    @Aggregation(pipeline = {
            "{ $match: { isRecommended: true } }",
            "{ $sample: { size: ?0 } }"
    })
    List<Product> findRandomRecommendedProducts(int size);

    List<Product> findByNameIgnoreCaseContaining(String name);
    List<Product> findAllByOrderByCustomerViewsDesc(Pageable pageable);
    List<Product> findAllByStockQuantityGreaterThanOrderByStockQuantityAsc(int stockQuantity, Pageable pageable);
    List<Product> findAllByOrderByCreatedDateDesc(Pageable pageable);
    List<Product> findAllByOrderByDiscountDesc(Pageable pageable);

}
