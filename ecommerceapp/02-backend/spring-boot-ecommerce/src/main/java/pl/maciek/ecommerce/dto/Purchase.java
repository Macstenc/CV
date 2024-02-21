package pl.maciek.ecommerce.dto;

import lombok.Data;
import pl.maciek.ecommerce.entity.Address;
import pl.maciek.ecommerce.entity.Customer;
import pl.maciek.ecommerce.entity.Order;
import pl.maciek.ecommerce.entity.OrderItem;

import java.util.Set;

@Data
public class Purchase {
    private Customer customer;
    private Address shippingAddress;
    private Address billingAddress;
    private Order order;
    private Set<OrderItem> orderItems;
}
