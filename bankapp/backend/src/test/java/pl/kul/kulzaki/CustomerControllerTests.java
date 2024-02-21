package pl.kul.kulzaki;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import pl.kul.kulzaki.Dto.CheckAccountExistResponse;
import pl.kul.kulzaki.Entity.Customer;
import pl.kul.kulzaki.Entity.Transaction;
import pl.kul.kulzaki.Services.CustomerService;
import pl.kul.kulzaki.Services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CustomerControllerTests {
    @Autowired
    private TransactionService transactionService;

    @Autowired
    @Mock
    private CustomerService customerService;

    @Test
    void saveTransactionShouldAddTransactionToCustomers() {
        String customerFromNumber = "8394857283942345";
        String customerToNumber = "3213337283942345";

        transactionService.saveTransaction(customerFromNumber, customerToNumber, 10);
        Transaction transaction = new Transaction(customerFromNumber, customerToNumber, 10);

        boolean hasTransaction1 = customerService.getCustomerByCardNumber(customerToNumber).getTransactions().contains(transaction);
        boolean hasTransaction2 = customerService.getCustomerByCardNumber(customerFromNumber).getTransactions().contains(transaction);

        assertEquals(true, hasTransaction1);
        assertEquals(true, hasTransaction2);
    }

    @Test
    void checkAccountExistShouldReturnTrue() {
        String validCustomerNumber = "8394857283942345";
        CheckAccountExistResponse response = transactionService.checkAccountExist(validCustomerNumber);
        assertTrue(response.exist);
        assertEquals("The provided number belongs to: Adam Rosolowski. Do you want to proceed with the operation?", response.message);
    }

    @Test
    void checkAccountExistShouldReturnFalse() {
        String invalidCustomerNumber = "invalidCustomerNumber";
        CheckAccountExistResponse response = transactionService.checkAccountExist(invalidCustomerNumber);
        assertFalse(response.exist);
        assertEquals("No associated bank account was found with the provided number. Try again!", response.message);
    }
}
