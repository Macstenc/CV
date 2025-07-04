package pl.kul.Sklep.Service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.kul.Sklep.Entity.Product;
import pl.kul.Sklep.Repository.ProductRepository;

public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    public ProductServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateProduct() {
        Product product = new Product();
        product.setName("Laptop");
        product.setDescription("Test laptop");
        product.setPrice(1000.0);
        product.setCategory("Electronics");
        product.setPurchaseCount(5);

        when(productRepository.save(product)).thenReturn(product);

        Product createdProduct = productService.createProduct(product);
        assertNotNull(createdProduct);
    }
}