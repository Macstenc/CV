package pl.kul.Sklep.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kul.Sklep.Entity.Order;
import pl.kul.Sklep.Entity.OrderItem;
import pl.kul.Sklep.Entity.Product;
import pl.kul.Sklep.Repository.OrderRepository;
import pl.kul.Sklep.Repository.ProductRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;


    public Order createOrder(Order order) {
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("Pending");

        for (OrderItem item : order.getItems()) {
            Optional<Product> optionalProduct = productRepository.findById(item.getProductId());

            if (optionalProduct.isEmpty()) {
                throw new RuntimeException("Produkt o ID " + item.getProductId() + " nie istnieje.");
            }

            Product product = optionalProduct.get();

            if (product.getStockQuantity() < item.getQuantity()) {
                throw new RuntimeException("Brak wystarczającej ilości produktu: " + product.getName());
            }

            product.setStockQuantity(product.getStockQuantity() - item.getQuantity());
            product.setStatus(productService.determineStatus(product.getStockQuantity()));
            product.setPurchaseCount(product.getPurchaseCount() + item.getQuantity());
            product.setLastPurchaseDate(LocalDate.now());

            productRepository.save(product);
        }

        return orderRepository.save(order);
    }


    public Optional<Order> getOrderById(String id) {
        return orderRepository.findById(id);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> updateOrderStatus(String id, String status) {
        Optional<Order> orderOptional = orderRepository.findById(id);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            order.setStatus(status);
            return Optional.of(orderRepository.save(order));
        }
        return Optional.empty();
    }

    public List<Order> getOrdersByUserEmail(String email) {
        return orderRepository.findByUserEmail(email);  // Assuming the method exists in the repository
    }
}
