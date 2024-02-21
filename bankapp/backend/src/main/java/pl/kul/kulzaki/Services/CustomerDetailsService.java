package pl.kul.kulzaki.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.kul.kulzaki.Entity.Customer;

import java.util.ArrayList;

@Service
public class CustomerDetailsService implements UserDetailsService {

    @Autowired
    private CustomerService customerService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Customer customer = customerService.getCustomerByEmail(email);

        return new org.springframework.security.core.userdetails.User(customer.getEmail(), customer.getPassword(), new ArrayList<>());
    }
}
