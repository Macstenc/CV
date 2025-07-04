package pl.kul.Sklep.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import pl.kul.Sklep.Entity.Category;
import pl.kul.Sklep.Entity.Product;
import pl.kul.Sklep.Repository.CategoryRepository;
import pl.kul.Sklep.Repository.ProductRepository;

import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin
@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CategoryService categoryService;

    public Product createProduct(Product product) {
        product.setStatus(determineStatus(product.getStockQuantity()));
        return productRepository.save(product);
    }


    public Product getProductbyId(String id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produkt nie istnieje"));
        if (product.getStockQuantity() <= 0) {
            throw new RuntimeException("Produkt niedostępny w magazynie.");
        }
        return product;
    }
    @Scheduled(cron = "0 0 0 * * ?")
    public void clearExpiredPromotions() {
        List<Product> products = productRepository.findAll();
        LocalDate today = LocalDate.now();
        for (Product p : products) {
            if (p.getPromotionEndDate() != null && p.getPromotionEndDate().isBefore(today)) {
                p.setDiscount(0);
                p.setDiscountedPrice(p.getPrice());
                productRepository.save(p);
            }
        }
    }


    public List<Product> getAllProducts() {
        return productRepository.findAll().stream()
                .filter(product -> product.getStockQuantity() > 0)
                .collect(Collectors.toList());
    }


    public Product updateProduct(Product product) {
        if (product.getId() == null || !productRepository.existsById(product.getId())) {
            throw new IllegalArgumentException("Produkt o podanym ID nie istnieje.");
        }
        product.setStatus(determineStatus(product.getStockQuantity()));
        return productRepository.save(product);
    }

    public String deleteProduct(String id) {
        productRepository.deleteById(id);
        return "Usunieto pozytywnie";
    }

    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategory(category).stream()
                .filter(product -> product.getStockQuantity() > 0)
                .collect(Collectors.toList());
    }


    public List<Product> getProductsByName(String name) {
        return productRepository.findByNameIgnoreCaseContaining(name).stream()
                .filter(product -> product.getStockQuantity() > 0)
                .collect(Collectors.toList());
    }

    public List<Product> productsByCategoryAndPrice(String category, double price) {
        return productRepository.findByCategoryAndPrice(category, price);
    }

    public List<Product> productsByCategoryOrPrice(String category, double price) {
        List<Product> tempList = productRepository.findByCategoryOrPrice(category, price);
        return tempList.stream()
                .filter(product -> product.getPrice() < price)
                .collect(Collectors.toList());
    }

    public List<Product> createProductList(List<Product> products) {
        return productRepository.saveAll(products);
    }

    public List<Product> getAllProductsWithPagination(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return productRepository.findAll(pageable).getContent();
    }


    public List<Product> allWithSorting() {
        Sort sort = Sort.by(Sort.Direction.ASC, "price");
        return productRepository.findAll(sort);
    }

    public List<Product> findCategoryLike(String category) {
        return productRepository.findByCategoryLike(category);
    }


    @Scheduled(cron = "0 0 0 1 * ?")
    public void resetPreviousMonthPurchases() {
        List<Product> products = productRepository.findAll();

        for (Product product : products) {
            if (product.getLastPurchaseDate() != null &&
                    product.getLastPurchaseDate().getMonthValue() == LocalDate.now().minusMonths(1).getMonthValue()) {

                product.setPurchaseCountPreviousMonth(product.getPurchaseCount());
                product.setPurchaseCount(0);
                productRepository.save(product);
            }
        }
    }

    public List<Product> nameStartsWith(String name) {
        return productRepository.findByNameStartingWith(name);
    }


    public void updatePriceRangesForAllProducts() {
        List<Category> categories = categoryRepository.findAll();

        for (Category category : categories) {
            double avgPrice = category.getAvgPrice();

            List<Product> productsInCategory = productRepository.findByCategory(category.getName());

            for (Product product : productsInCategory) {
                double lowerThreshold = avgPrice * 0.75;
                double upperThreshold = avgPrice * 1.25;

                if (product.getPrice() < lowerThreshold) {
                    product.setPriceRange("low");
                } else if (product.getPrice() >= lowerThreshold && product.getPrice() <= upperThreshold) {
                    product.setPriceRange("medium");
                } else {
                    product.setPriceRange("high");
                }

                productRepository.save(product);
            }
        }
    }
    public String determineStatus(int stockQuantity) {
        return stockQuantity > 0 ? "Available" : "Out of Stock";
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void scheduledPriceRangeUpdate() {
        updatePriceRangesForAllProducts();
    }

    public void assignPhotosToProducts() {
        List<Product> products = productRepository.findAll();

        for (Product product : products) {
            String categoryFolder = "assets/" + product.getCategory();

            List<String> photoPaths = Arrays.asList(
                    Paths.get(categoryFolder, product.getCategory() + "1.jpg").toString(),
                    Paths.get(categoryFolder, product.getCategory() + "2.jpg").toString(),
                    Paths.get(categoryFolder, product.getCategory() + "3.jpg").toString()
            );

            Random random = new Random();
            String mainPhoto = photoPaths.get(random.nextInt(photoPaths.size()));

            product.setMainPhoto(mainPhoto);
            product.setPhotos(new ArrayList<>(photoPaths));

            productRepository.save(product);
        }

    }
    public void assignRandomValuesToNewFields() {
        List<Product> products = productRepository.findAll();
        Random random = new Random();

        for (Product product : products) {
            product.setStockQuantity(random.nextInt(100)); // Ilość w magazynie od 0 do 100
            product.setSupplier("Supplier_" + random.nextInt(10)); // Losowy dostawca
            product.setBrand("Brand_" + random.nextInt(5)); // Losowa marka
            product.setModel("Model_" + random.nextInt(50)); // Losowy model
            product.setDiscount(random.nextDouble() * 50); // Zniżka od 0% do 50%
            product.setDiscountedPrice(product.getPrice() - (product.getPrice() * (product.getDiscount() / 100)));
            product.setPromotionEndDate(LocalDate.now().plusDays(random.nextInt(30))); // Losowa data końca promocji (maks. 30 dni)
            product.setAverageRating(random.nextDouble() * 5); // Ocena od 0.0 do 5.0
            product.setReviews(Arrays.asList("Review_" + random.nextInt(100), "Review_" + random.nextInt(100))); // Losowe recenzje
            product.setRecommended(random.nextBoolean()); // Polecany: tak/nie
            product.setCustomerViews(random.nextInt(1000)); // Liczba wyświetleń od 0 do 1000
            product.setCreatedDate(LocalDate.now().minusDays(random.nextInt(100))); // Losowa data utworzenia
            product.setUpdatedDate(LocalDate.now());

            productRepository.save(product);
        }
    }
    public List<Product> getProductsByCategoryWithFilters(
            String category,
            Double minPrice,
            Double maxPrice,
            String sortBy,
            String direction,
            boolean recommended) {
        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        List<Product> products = productRepository.findByCategory(category, sort);

        if (minPrice != null) {
            products = products.stream().filter(product -> product.getPrice() >= minPrice).collect(Collectors.toList());
        }
        if (maxPrice != null) {
            products = products.stream().filter(product -> product.getPrice() <= maxPrice).collect(Collectors.toList());
        }

        if (recommended) {
            products = products.stream().filter(Product::isRecommended).collect(Collectors.toList());
        }
        products = products.stream()
                .filter(product -> product.getStockQuantity() > 0)
                .collect(Collectors.toList());


        return products;
    }



    public double getMaxPriceByCategory(String category) {
        return productRepository.findByCategory(category)
                .stream()
                .mapToDouble(Product::getPrice)
                .max()
                .orElse(0); // Zwraca 0, jeśli brak produktów
    }


    public List<Product> getRecommendedProducts(int pageNo, int pageSize) {
        int totalSize = pageSize;
        return productRepository.findRandomRecommendedProducts(totalSize);
    }


    public List<Product> getMostViewedProducts(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return productRepository.findAllByOrderByCustomerViewsDesc(pageable);
    }

    public List<Product> getNewestProducts(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return productRepository.findAllByOrderByCreatedDateDesc(pageable);
    }
    public List<Product> getLowStockProducts(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return productRepository.findAllByStockQuantityGreaterThanOrderByStockQuantityAsc(0, pageable); // Example, assuming stockQuantity > 0
    }
    public List<Product> getHighestDiscountedProducts(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, Sort.by(Sort.Direction.DESC, "discount"));
        return productRepository.findAllByOrderByDiscountDesc(pageable);
    }


    public List<Product> getSimilarProducts(String category, String productId, double currentPrice) {
        double priceRangeFactor = 0.2;

        List<Product> products = productRepository.findByCategory(category).stream()
                .filter(product -> product.getStockQuantity() > 0)
                .filter(product -> !product.getId().equals(productId))
                .filter(product -> product.getPrice() >= currentPrice * (1 - priceRangeFactor) &&
                        product.getPrice() <= currentPrice * (1 + priceRangeFactor))
                .sorted(Comparator.comparing(Product::getCustomerViews).reversed())
                .limit(20)
                .collect(Collectors.toList());

        if (products.size() < 5) {
            List<Product> additionalProducts = productRepository.findByCategory(category).stream()
                    .filter(product -> !product.getId().equals(productId) && !products.contains(product))
                    .limit(5 - products.size())
                    .collect(Collectors.toList());
            products.addAll(additionalProducts);
        }

        return products.stream().limit(20).collect(Collectors.toList());
    }




    public List<Product> getRelatedCategoryProducts(List<String> relatedCategories, double currentPrice, double priceRangeFactor) {
        List<Product> products = productRepository.findAll().stream()
                .filter(product -> product.getStockQuantity() > 0)
                .filter(product -> relatedCategories.contains(product.getCategory()))
                .filter(product -> product.getPrice() >= currentPrice * (1 - priceRangeFactor) &&
                        product.getPrice() <= currentPrice * (1 + priceRangeFactor))
                .sorted((p1, p2) -> {
                    int discountComparison = Double.compare(p2.getDiscount(), p1.getDiscount());
                    if (discountComparison != 0) {
                        return discountComparison;
                    }
                    return Integer.compare(p2.getCustomerViews(), p1.getCustomerViews());
                })
                .limit(20)
                .collect(Collectors.toList());

        if (products.size() < 5) {
            List<Product> additionalProducts = productRepository.findAll().stream()
                    .filter(product -> relatedCategories.contains(product.getCategory()) && !products.contains(product))
                    .limit(5 - products.size())
                    .collect(Collectors.toList());
            products.addAll(additionalProducts);
        }

        return products.stream().limit(20).collect(Collectors.toList());
    }


    public void generateProductsForAllCategories(int numberOfProductsPerCategory) {
        List<Category> categories = categoryRepository.findAll();
        Random random = new Random();

        for (Category category : categories) {
            List<Product> products = new ArrayList<>();

            for (int i = 1; i <= numberOfProductsPerCategory; i++) {
                Product product = new Product();
                product.setName(category.getName() + " " + i);
                product.setDescription(generateProductDescription(category.getName()));
                product.setPrice(10 + random.nextDouble() * 9000); // Cena losowa w zakresie 100-1000
                product.setCategory(category.getName());
                product.setPurchaseCount(random.nextInt(500));
                product.setPurchaseCountPreviousMonth(random.nextInt(100));
                product.setLastPurchaseDate(LocalDate.now().minusDays(random.nextInt(365)));
                product.setRelatedCategories(generateRandomRelatedCategories(category.getName(), categories, random));

                String categoryPath = "assets/" + category.getName();
                int mainPhotoIndex = random.nextInt(3) + 1; // Losowa liczba od 1 do 3
                product.setMainPhoto(Paths.get(categoryPath, category.getName() + mainPhotoIndex + ".jpg").toString());
                product.setPhotos(Arrays.asList(
                        Paths.get(categoryPath, category.getName() + "1.jpg").toString(),
                        Paths.get(categoryPath, category.getName() + "2.jpg").toString(),
                        Paths.get(categoryPath, category.getName() + "3.jpg").toString()
                ));

                product.setPriceRange(determinePriceRange(category.getAvgPrice(), product.getPrice()));
                product.setStockQuantity(random.nextInt(100));
                product.setSupplier("Supplier_" + random.nextInt(10));
                product.setBrand("Brand_" + random.nextInt(5));
                product.setModel("Model_" + random.nextInt(50));
                product.setDiscount(random.nextDouble() * 50);
                product.setDiscountedPrice(product.getPrice() - (product.getPrice() * (product.getDiscount() / 100)));
                product.setPromotionEndDate(LocalDate.now().plusDays(random.nextInt(30)));
                product.setAverageRating(random.nextDouble() * 5);
                product.setReviews(Arrays.asList("Review_" + random.nextInt(100), "Review_" + random.nextInt(100)));
                product.setRecommended(random.nextBoolean());
                product.setCustomerViews(random.nextInt(1000));
                product.setCreatedDate(LocalDate.now().minusDays(random.nextInt(100)));
                product.setUpdatedDate(LocalDate.now());
                products.add(product);
            }

            productRepository.saveAll(products);
        }
    }
    private String generateProductDescription(String categoryName) {
        return String.join(" ",
                "Produkt z kategorii " + categoryName + " został zaprojektowany z myślą o najwyższej jakości wykonania.",
                "Charakteryzuje się solidną konstrukcją oraz nowoczesnym designem, który przyciąga wzrok.",
                "Idealnie sprawdzi się zarówno w codziennym użytkowaniu, jak i w bardziej wymagających warunkach.",
                "Dzięki zastosowaniu zaawansowanych technologii, oferuje wysoką wydajność oraz niezawodność.",
                "Materiał użyty do produkcji jest odporny na uszkodzenia mechaniczne oraz działanie czynników zewnętrznych.",
                "Intuicyjna obsługa oraz ergonomiczna forma czynią ten produkt przyjaznym dla użytkownika.",
                "Sprawdzi się w domu, biurze, a także w profesjonalnym zastosowaniu.",
                "W zestawie znajduje się komplet akcesoriów, które zwiększają funkcjonalność urządzenia.",
                "Produkt przeszedł rygorystyczne testy jakości, co potwierdza jego niezawodność.",
                "To idealny wybór dla osób ceniących sobie trwałość, nowoczesność i komfort użytkowania."
        );
    }

    private List<String> generateRandomRelatedCategories(String currentCategory, List<Category> allCategories, Random random) {
        List<String> relatedCategories = new ArrayList<>();
        List<Category> otherCategories = allCategories.stream()
                .filter(category -> !category.getName().equals(currentCategory))
                .collect(Collectors.toList());

        for (int i = 0; i < 4; i++) {
            relatedCategories.add(otherCategories.get(random.nextInt(otherCategories.size())).getName());
        }
        return relatedCategories;
    }

    private String determinePriceRange(double avgPrice, double productPrice) {
        double lowerThreshold = avgPrice * 0.75;
        double upperThreshold = avgPrice * 1.25;

        if (productPrice < lowerThreshold) {
            return "low";
        } else if (productPrice <= upperThreshold) {
            return "medium";
        } else {
            return "high";
        }
    }

}

