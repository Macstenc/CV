package pl.kul.kulzaki.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.kul.kulzaki.Dto.LoginRequest;
import pl.kul.kulzaki.Dto.LoginResponse;
import pl.kul.kulzaki.Entity.Customer;
import pl.kul.kulzaki.Services.CustomerService;
import pl.kul.kulzaki.Util.JwtTokenUtil;

import java.util.ArrayList;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody Customer customer) {
        System.out.println(customer.toString());
        // Check if customer exists already
        if (customerService.doesCustomerExist(customer.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Customer already exists");
        }
        System.out.println("t1");

        // If customer doesn't exist, encode password and save
        customer.setPassword(customer.getPassword());
        System.out.println("t2");
        customerService.addCustomer(customer);

        return ResponseEntity.ok("Customer registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginRequest authenticationRequest) throws Exception {
        String email = authenticationRequest.getEmail();
        if(!this.customerService.doesCustomerExist(email)) return ResponseEntity.ok("User not found!");
        Customer customer = customerService.getCustomerByEmail(email);
        if(customer.getPassword().equals(authenticationRequest.getPassword())){
            final String token = jwtTokenUtil.generateToken(authenticationRequest.getEmail());
            return ResponseEntity.ok(new LoginResponse(token));
        }else return ResponseEntity.ok("Wrong password, try again!");
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
