package pl.kul.kulzaki.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import pl.kul.kulzaki.Dto.CheckAccountExistResponse;
import pl.kul.kulzaki.Entity.Customer;
import pl.kul.kulzaki.Services.CustomerService;
import pl.kul.kulzaki.Services.TransactionService;
import pl.kul.kulzaki.Util.JwtTokenUtil;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerService customerService;

    @Autowired
    TransactionService transactionService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/add")
    public String addCustomers(@RequestBody List<Customer> customers) {
        customerService.addCustomers(customers);
        return "Customers added successfully!";
    }

    @GetMapping("/list")
    public ResponseEntity<List<Customer>> getCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @GetMapping("/getCustomer")
    public Customer getCustomer(@RequestHeader("Authorization") String authorization) {
        String jwt = null;
        String email = null;
        if (authorization != null && authorization.startsWith("Bearer ")) {
            jwt = authorization.substring(7);
            email = jwtTokenUtil.getUsernameFromToken(jwt);
            return customerService.getCustomerByEmail(email);
        } else
            return null;

    }


    @PostMapping("/transaction")
    public String transaction(@RequestHeader("Authorization") String authorization, @RequestBody Map<String, Object> requestBody) {
        String customerfrom = (String) requestBody.get("customerfrom");
        String customerto = (String) requestBody.get("customerto");
        int amount = (int) requestBody.get("amount");
        String jwt = null;
        if (authorization != null && authorization.startsWith("Bearer ")) {
            jwt = authorization.substring(7);
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String usernameFromToken = userDetails.getUsername();
        String customerfromEmail=customerService.getCustomerByCardNumber(customerfrom).getEmail();
            if (usernameFromToken.equals(customerfromEmail)&&customerfrom!=customerto) {
                Customer customerFrom= customerService.getCustomerByCardNumber(customerfrom);
                Customer customerTo= customerService.getCustomerByCardNumber(customerto);
                customerFrom.setBalance(customerFrom.getBalance()-amount);
                customerTo.setBalance(customerTo.getBalance()+amount);
                transactionService.saveTransaction(customerfrom, customerto, amount);

                System.out.println(jwt + " " + customerfrom + " " + customerto + " " + amount);


                return "Transaction success";
            }
        }
        return "Incorrect sender";
    }
    @PostMapping("/withdrawls")
    public String withdrawl(@RequestHeader("Authorization") String authorization,@RequestBody Map<String, Object> requestBody){
        String cardnumber = (String) requestBody.get("cardnumber");
        int pin = (int) requestBody.get("pin");
        int amount = (int) requestBody.get("amount");
        if (authorization != null && authorization.startsWith("Bearer ")) {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String usernameFromToken = userDetails.getUsername();
            String emailtemp=customerService.getCustomerByCardNumber(cardnumber).getEmail();

            int pintemp = customerService.getCustomerByEmail(usernameFromToken).getPin();

            if (usernameFromToken.equals(emailtemp)&&pin==pintemp){
                Customer customer= customerService.getCustomerByCardNumber(cardnumber);

                if (customer.getBalance() >= amount) {
                    customer.setBalance(customer.getBalance() - amount);
                    return "Cash withdrawal success";
                }else
                    return "Not enough balance";

            }else
                return "Incorrect pin or card number";
        }
        return "Incorrect token";
    }

    @GetMapping("/checkAccountExist")
    public CheckAccountExistResponse checkAccountExist(@RequestHeader("Authorization") String jwtToken, @RequestBody Map<String, Object> requestBody) {
        String customerto = (String) requestBody.get("customerto");
        return transactionService.checkAccountExist(customerto);
    }
}
