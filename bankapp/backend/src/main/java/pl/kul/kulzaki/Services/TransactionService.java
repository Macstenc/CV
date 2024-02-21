package pl.kul.kulzaki.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kul.kulzaki.Dto.CheckAccountExistResponse;
import pl.kul.kulzaki.Entity.Customer;
import pl.kul.kulzaki.Entity.Transaction;

@Service
public class TransactionService {
    @Autowired
    private CustomerService customerService;

    @Autowired
    public TransactionService(CustomerService customerService) {
        this.customerService = customerService;
    }

    public void saveTransaction(String customerfromnumber,String customertonumer,int amount){
        Customer customerBycardNumberfrom = customerService.getCustomerByCardNumber(customerfromnumber);
        Customer customerBycardNumberto = customerService.getCustomerByCardNumber(customertonumer);
        Transaction transaction = new Transaction(customerfromnumber,customertonumer, amount);
        customerBycardNumberfrom.addTransaction(transaction);
        customerBycardNumberto.addTransaction(transaction);
    }

    public CheckAccountExistResponse checkAccountExist(String customerto){
        Customer customer = customerService.getCustomerByCardNumber(customerto);
        if(customer != null) {
            return new CheckAccountExistResponse(true, "The provided number belongs to: "+customer.getName()+ " "+ customer.getLastname() +". Do you want to proceed with the operation?");
        }else{
            return new CheckAccountExistResponse(false, "No associated bank account was found with the provided number. Try again!");
        }
    }
}
