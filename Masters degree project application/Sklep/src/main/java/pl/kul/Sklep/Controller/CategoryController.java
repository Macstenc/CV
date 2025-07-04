package pl.kul.Sklep.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.kul.Sklep.Entity.Category;
import pl.kul.Sklep.Entity.Product;
import pl.kul.Sklep.Service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @PostMapping("/create")
    public Category create(@RequestBody Category category){
        return categoryService.createCategory(category);
    }
    @PostMapping("/createlist")
    public List<Category> createCategoryList(@RequestBody List<Category> categories){
        return  categoryService.createCategoryList(categories);
    }
    @GetMapping("/calculateAvgPrice")
    public ResponseEntity<String> calculateAvgPrice() {
        categoryService.calculateAvgPriceForAllCategories();
        return ResponseEntity.ok("Średnie ceny zostały obliczone.");
    }
    @GetMapping("/all")
    public List<Category> getAllCategories(){return categoryService.getAllCategories();}
}
