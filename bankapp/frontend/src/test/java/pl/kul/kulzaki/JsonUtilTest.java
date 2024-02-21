package pl.kul.kulzaki;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import pl.kul.kulzaki.Models.Customer;
import pl.kul.kulzaki.Models.Transaction;
import pl.kul.kulzaki.Util.JsonUtil;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class JsonUtilTest {

    @Test
    public void getJWTFromJson_shouldReturnJWTToken() {
        String json = "{\"jwtToken\":\"token123\"}";
        String jwt = JsonUtil.getJWTFromJson(json);
        assertEquals("token123", jwt, "JWT token should be extracted correctly");
    }

    @Test
    public void jsonToCustomer_shouldConvertJsonToCustomerObject() {
        String jsonString = """
                {
                  "name": "John",
                  "lastname": "Doe",
                  "email": "john.doe@example.com",
                  "password": "password123",
                  "phonenumber": "123456789",
                  "pesel": "12345678901",
                  "address": "123 Main St",
                  "datecreated": "2024-01-01T00:00:00",
                  "cardNumber": "1234567890123456",
                  "pin": 1234,
                  "balance": 1000,
                  "transactions": [
                    {
                      "senderAccount": "1234567890123456",
                      "receiverAccount": "6543210987654321",
                      "amount": 100
                    }
                  ]
                }""";

        JSONObject json = new JSONObject(jsonString);
        Customer customer = JsonUtil.jsonToCustomer(json);

        assertNotNull(customer, "Customer object should not be null");
        assertEquals("John", customer.getName(), "Name should match");
        assertEquals("Doe", customer.getLastname(), "Lastname should match");
        assertEquals("john.doe@example.com", customer.getEmail(), "Email should match");
        assertEquals("password123", customer.getPassword(), "Password should match");
        assertEquals("123456789", customer.getPhonenumber(), "Phone number should match");
        assertEquals("12345678901", customer.getPesel(), "PESEL should match");
        assertEquals("123 Main St", customer.getAddress(), "Address should match");
        assertEquals(LocalDateTime.of(2024, 1, 1, 0, 0), customer.getDatecreated(), "Date created should match");
        assertEquals("1234567890123456", customer.getCardNumber(), "Card number should match");
        assertEquals(1234, customer.getPin(), "PIN should match");
        assertEquals(1000, customer.getBalance(), "Balance should match");

        List<Transaction> transactions = customer.getTransactions();
        assertNotNull(transactions, "Transactions list should not be null");
        assertEquals(1, transactions.size(), "Transactions list size should match");
        Transaction transaction = transactions.get(0);
        assertEquals("1234567890123456", transaction.getSenderAccount(), "Sender account should match");
        assertEquals("6543210987654321", transaction.getReceiverAccount(), "Receiver account should match");
        assertEquals(100, transaction.getAmount(), "Amount should match");
    }
}
