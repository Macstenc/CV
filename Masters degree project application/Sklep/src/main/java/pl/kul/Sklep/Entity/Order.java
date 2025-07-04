package pl.kul.Sklep.Entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Document(collection = "orders")
public class Order {
    @Id
    private String id;
    private String userName;
    private String userLastName;
    private String userEmail;
    private String userPhone;
    private List<OrderItem> items;
    private Address shippingAddress;
    private double totalPrice;
    private LocalDateTime orderDate;
    private String status;
    private PaymentInfo paymentInfo;
}
