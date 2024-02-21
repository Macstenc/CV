package pl.kul.kulzaki.Services;

import org.springframework.stereotype.Service;
import pl.kul.kulzaki.Entity.Customer;
import pl.kul.kulzaki.Storages.CustomerStorage;

import java.util.List;
import java.util.Objects;

@Service
public class CustomerService {
    public List<Customer> getAllCustomers() {
        return CustomerStorage.getAllCustomers();
    }

    public void addCustomers(List<Customer> customers) {
        for (Customer customer : customers) {
            addCustomer(customer);
        }
    }

    public Customer getCustomerByEmail(String email) {
        for (Customer customer : getAllCustomers()) {
            if (Objects.equals(customer.getEmail(), email)) return customer;
        }
        return null;
    }

    public void addCustomer(Customer customer) {
        CustomerStorage.addCustomer(customer);
    }

    public boolean doesCustomerExist(String email) {
        for (Customer customer :
                getAllCustomers()) {
            if (customer.getEmail().equals(email))
                return true;
        }
        return false;
    }

    public Customer getCustomerByCardNumber(String cardnumber) {
        for (Customer customer : getAllCustomers()) {
            if (Objects.equals(customer.getCardNumber(), cardnumber)) return customer;
        }
        return null;
    }

}
