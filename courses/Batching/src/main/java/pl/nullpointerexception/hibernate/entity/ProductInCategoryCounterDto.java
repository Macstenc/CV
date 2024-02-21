package pl.nullpointerexception.hibernate.entity;

public class ProductInCategoryCounterDto {
    private Long categoryId;
    private Long productInCategoryCounter;

    public ProductInCategoryCounterDto(Long categoryId, Long productInCategoryCounter) {
        this.categoryId = categoryId;
        this.productInCategoryCounter = productInCategoryCounter;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public Long getProductInCategoryCounter() {
        return productInCategoryCounter;
    }
}
