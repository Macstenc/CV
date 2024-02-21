package pl.kul.kulzaki.Storages;

import pl.kul.kulzaki.Entity.Customer;

import java.math.BigInteger;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CustomerStorage {
    private static List<Customer> customers = new ArrayList<>();
    static {
        Customer customer1 = new Customer("Adam", "Rosolowski", "AdamR@gmail.com","Adam" ,"679523423", "92384758291", "Lublin A", LocalDateTime.now(), "8394857283942345", 5678,10000);
        Customer customer2 = new Customer("Adrian", "Proscinski", "AdrianP@gmail.com","Adrian" ,"987654321", "42384358291", "Lublin A", LocalDateTime.now(), "3214857283942345", 1234,20000);
        Customer customer3 = new Customer("Maciek", "Stencel", "MaciekS@gmail.com","Maciek", "327654321", "42383258291", "Lublin M", LocalDateTime.now(), "3213337283942345", 4321,30000);

        customers.add(customer1);
        customers.add(customer2);
        customers.add(customer3);
    }

    public static void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public static List<Customer> getAllCustomers() {
        return customers;
    }
}