package pl.nullpointerexception.hibernate.entity;

public class ReviewDto {
    private Long id;
    private String content;
    private int rating;

    public ReviewDto(Long id, String content, int rating) {
        this.id = id;
        this.content = content;
        this.rating = rating;
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public int getRating() {
        return rating;
    }
}
