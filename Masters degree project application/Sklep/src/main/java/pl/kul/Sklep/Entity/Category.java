package pl.kul.Sklep.Entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "category")
public class Category {
    @Id
    private String id;
    @Indexed(unique = true)
    private String name;
    private double avgPrice;
}
