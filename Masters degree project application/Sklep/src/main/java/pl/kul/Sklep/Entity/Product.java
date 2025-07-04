package pl.kul.Sklep.Entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Data
@Document(collection = "products")
public class Product {
    @Id
    private String id;
    private String name;
    private String description;
    private double price;
    private String category;
    private int purchaseCount;
    private int purchaseCountPreviousMonth;
    private LocalDate lastPurchaseDate;
    private List<String> relatedCategories;
    private String mainPhoto;
    private List<String> photos;
    private String priceRange;

    private int stockQuantity;
    private String supplier;
    private String brand;
    private String model;
    private double discount;
    private double discountedPrice;
    private LocalDate promotionEndDate;
    private double averageRating;
    private List<String> reviews;
    private boolean isRecommended;
    private int customerViews;
    private LocalDate createdDate;
    private LocalDate updatedDate;
    private String status;
}
