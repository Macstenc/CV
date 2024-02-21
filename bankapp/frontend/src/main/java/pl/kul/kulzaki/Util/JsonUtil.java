package pl.kul.kulzaki.Util;

import org.json.JSONArray;
import org.json.JSONObject;
import pl.kul.kulzaki.Models.Customer;
import pl.kul.kulzaki.Models.Transaction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class JsonUtil {

    public static String getJWTFromJson(String json) {
        JSONObject jsonObject = new JSONObject(json);
        return jsonObject.getString("jwtToken");
    }

    public static Customer jsonToCustomer(JSONObject json) {
        String name = json.getString("name");
        String lastname = json.getString("lastname");
        String email = json.getString("email");
        String password = json.getString("password"); // Ensure this is secure and appropriate
        String phonenumber = json.getString("phonenumber");
        String pesel = json.getString("pesel");
        String address = json.getString("address");
        LocalDateTime datecreated = LocalDateTime.parse(json.getString("datecreated"));
        String cardNumber = json.getString("cardNumber");
        int pin = json.getInt("pin");
        int balance = json.getInt("balance");
        JSONArray transactionsJson = json.getJSONArray("transactions");
        List<Transaction> transactions = new ArrayList<>();

        for (int i = 0; i < transactionsJson.length(); i++) {
            JSONObject transactionJson = transactionsJson.getJSONObject(i);
            Transaction transaction = new Transaction(
                    transactionJson.getString("senderAccount"),
                    transactionJson.getString("receiverAccount"),
                    transactionJson.getInt("amount")
            );
            transactions.add(transaction);
        }

        return new Customer(name, lastname, email, password, phonenumber, pesel, address, datecreated, cardNumber, pin, balance, transactions);
    }
}
