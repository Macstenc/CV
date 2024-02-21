package pl.kul.kulzaki.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
public class Customer {
    private String name;
    private String lastname;
    private String email;
    private String password;
    private String phonenumber;
    private String pesel;
    private String address;
    private LocalDateTime datecreated;
    private String cardNumber;
    private int pin;
    private int balance;

    private List<Transaction> transactions=new ArrayList<>();
    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);

    }

    public Customer(String name, String lastname, String email, String password, String phonenumber, String pesel, String address, LocalDateTime datecreated, String cardNumber, int pin, int balance) {
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.phonenumber = phonenumber;
        this.pesel = pesel;
        this.address = address;
        this.datecreated = datecreated;
        this.cardNumber = cardNumber;
        this.pin = pin;
        this.balance = balance;
    }
}
