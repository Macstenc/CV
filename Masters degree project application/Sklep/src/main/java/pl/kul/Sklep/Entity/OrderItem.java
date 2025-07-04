package pl.kul.Sklep.Entity;

import lombok.Data;

@Data
public class OrderItem {
    private String productId;
    private String productName;
    private int quantity;
    private double price;
    private double totalPrice;
}
