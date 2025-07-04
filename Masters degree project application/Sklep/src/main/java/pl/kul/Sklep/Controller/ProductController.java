package pl.kul.Sklep.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.kul.Sklep.Entity.Product;
import pl.kul.Sklep.Service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    ProductService productService;

    @PostMapping("/create")
    public Product createProduct(@RequestBody Product product){
        return  productService.createProduct(product);
    }
    @PostMapping("/createlist")
    public List<Product> createProductList(@RequestBody List<Product> product){
        return  productService.createProductList(product);
    }
    @GetMapping("/getById/{id}")
    public Product getProductbyId(@PathVariable String id){
        return productService.getProductbyId(id);
    }
    @GetMapping("/all")
    public List<Product> getAllProducts(){
        return productService.getAllProducts();
    }
    @GetMapping("/allWithPagination")
    public List<Product> getAllProductsWithPagination(@RequestParam int pageNo,@RequestParam int pageSize){
        return productService.getAllProductsWithPagination(pageNo,pageSize);
    }
    @PostMapping("/assignRandomValues")
    public ResponseEntity<String> assignRandomValuesToAllProducts() {
        productService.assignRandomValuesToNewFields();
        return ResponseEntity.ok("Random values assigned to all products.");
    }

    @GetMapping("/productsByCategoryWithFilters")
    public List<Product> getProductsByCategoryWithFilters(
            @RequestParam String category,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false, defaultValue = "price") String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String direction,
            @RequestParam(required = false, defaultValue = "false") boolean recommended) {
        return productService.getProductsByCategoryWithFilters(category, minPrice, maxPrice, sortBy, direction, recommended);
    }
    @GetMapping("/maxPrice")
    public double getMaxPriceByCategory(@RequestParam String category) {
        return productService.getMaxPriceByCategory(category);
    }


    @PutMapping("/update")
    public Product updateProduct(@RequestBody Product product){
        return productService.updateProduct(product);
    }
    @DeleteMapping("/delete/{id}")
    public String deleteProduct(@PathVariable String id){
        return productService.deleteProduct(id);
    }
    @GetMapping("/productsbyCategory/{category}")
    public List<Product> productsByCategory(@PathVariable String category){
        return productService.getProductsByCategory(category);

    }
    @GetMapping("/productsbyName/{name}")
    public List<Product> productsByName(@PathVariable String name) {
        if (name == null || name.isEmpty()) {
            return productService.getAllProducts();
        }
        return productService.getProductsByName(name);
    }


    @GetMapping("/productsbyCategoryAndPrice")
    public List<Product> productsByCategoryAndPrice(@RequestParam String category,@RequestParam double price){
        return productService.productsByCategoryAndPrice(category,price);
    }
    @GetMapping("/productsbyCategoryOrPrice")
    public List<Product> productsByCategoryOrPrice(@RequestParam String category,@RequestParam double price) {
    return productService.productsByCategoryOrPrice(category,price);
    }
    @GetMapping("/allWithSorting")
    public List<Product> allWithSorting(){
        return productService.allWithSorting();
    }
    @GetMapping("/findCategorylike")
    public List<Product> findCategoryLike(@RequestParam String category){
        return productService.findCategoryLike(category);
    }
    @GetMapping("/nameStartsWith")
    public List<Product> nameStartsWith(@RequestParam String name){
        return productService.nameStartsWith(name);
    }
    @GetMapping("/updatePriceRanges")
    public ResponseEntity<String> updatePriceRanges() {
        productService.updatePriceRangesForAllProducts();
        return ResponseEntity.ok("Price ranges updated for all products");
    }
    @PostMapping("/assignPhotos")
    public ResponseEntity<String> assignPhotosToProducts() {
        productService.assignPhotosToProducts();
        return ResponseEntity.ok("Zdjęcia zostały przypisane do produktów.");
    }
    @GetMapping("/recommendedWithPagination")
    public List<Product> getRecommendedWithPagination(@RequestParam int pageNo, @RequestParam int pageSize) {
        return productService.getRecommendedProducts(pageNo, pageSize);
    }
    @GetMapping("/mostViewedWithPagination")
    public List<Product> getMostViewedWithPagination(@RequestParam int pageNo, @RequestParam int pageSize) {
        return productService.getMostViewedProducts(pageNo, pageSize);
    }
    @GetMapping("/newestWithPagination")
    public List<Product> getNewestWithPagination(@RequestParam int pageNo, @RequestParam int pageSize) {
        return productService.getNewestProducts(pageNo, pageSize);
    }
    @GetMapping("/lowStockWithPagination")
    public List<Product> getLowStockWithPagination(@RequestParam int pageNo, @RequestParam int pageSize) {
        return productService.getLowStockProducts(pageNo, pageSize);
    }
    @GetMapping("/highestDiscountWithPagination")
    public List<Product> getHighestDiscountWithPagination(@RequestParam int pageNo, @RequestParam int pageSize) {
        return productService.getHighestDiscountedProducts(pageNo, pageSize);
    }

    @GetMapping("/similarProducts/{productId}")
    public List<Product> getSimilarProducts(@PathVariable String productId) {
        Product currentProduct = productService.getProductbyId(productId);
        return productService.getSimilarProducts(
                currentProduct.getCategory(),
                productId,
                currentProduct.getPrice()
        );
    }

    @GetMapping("/relatedProducts/{productId}")
    public List<Product> getRelatedProducts(@PathVariable String productId) {
        Product currentProduct = productService.getProductbyId(productId);
        return productService.getRelatedCategoryProducts(
                currentProduct.getRelatedCategories(),
                currentProduct.getPrice(),
                0.25
        );
    }

    @PostMapping("/generateProducts")
    public ResponseEntity<String> generateProductsForAllCategories(@RequestParam int numberOfProductsPerCategory) {
        productService.generateProductsForAllCategories(numberOfProductsPerCategory);
        return ResponseEntity.ok("Wygenerowano " + numberOfProductsPerCategory + " produktów dla każdej kategorii.");
    }


}
