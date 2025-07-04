package pl.kul.Sklep.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import pl.kul.Sklep.Entity.Category;
import pl.kul.Sklep.Entity.Product;
import pl.kul.Sklep.Repository.CategoryRepository;
import pl.kul.Sklep.Repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;
@CrossOrigin
@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ProductRepository productRepository;
    public List<Category> getAllCategories(){return categoryRepository.findAll();}
    public Category createCategory(Category category){
        if (categoryRepository.existsByName(category.getName())) {
            throw new IllegalArgumentException("Kategoria o nazwie " + category.getName() + " już istnieje.");
        }
        return categoryRepository.save(category);
    }

    public List<Category> createCategoryList(List<Category> categories) {
        List<Category> categoriesToSave = new ArrayList<>();

        for (Category category : categories) {
            if (!categoryRepository.existsByName(category.getName())) {
                categoriesToSave.add(category);
            }
        }

        return categoryRepository.saveAll(categoriesToSave);
    }
    public void calculateAvgPriceForAllCategories() {
        List<Category> categories = categoryRepository.findAll();

        for (Category category : categories) {
            List<Product> products = productRepository.findByCategory(category.getName());

            if (!products.isEmpty()) {
                double avgPrice = products.stream()
                        .mapToDouble(Product::getPrice)
                        .average()
                        .orElse(0.0);

                category.setAvgPrice(avgPrice);
                categoryRepository.save(category);
            }
        }
    }
    @Scheduled(cron = "0 0 0 * * ?")
    public void scheduleAvgPriceCalculation() {
       calculateAvgPriceForAllCategories();
    }
}