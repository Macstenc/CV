package pl.nullpointerexception.hibernate.entity.batch;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "review")
public class BatchReview {
    @Id
    private Long id;
    private String content;
    private int rating;
    @Column(name = "product_id")
    private Long productId;

    public BatchReview() {
    }

    public BatchReview(Long id, String content, int rating, Long productId) {
        this.id = id;
        this.content = content;
        this.rating = rating;
        this.productId = productId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    @Override
    public String toString() {
        return "BatchReview{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", rating=" + rating +
                ", productId=" + productId +
                '}';
    }
}
