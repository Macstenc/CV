package pl.kul.kulzaki.Screens;

import pl.kul.kulzaki.Action;
import pl.kul.kulzaki.Models.Customer;
import pl.kul.kulzaki.Screen;
import pl.kul.kulzaki.Store.CustomerService;

import java.util.Scanner;

public class DetailsScreen implements Screen {

    private final CustomerService customerService;

    public DetailsScreen(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    public void process() {
        Customer customer = customerService.getCustomer();

        System.out.println("Account details:");
        System.out.println("Name: " + customer.getName());
        System.out.println("Lastname: " + customer.getLastname());
        System.out.println("Card number: " + customer.getCardNumber());
        System.out.println("E-mail: " + customer.getEmail());
        System.out.println("Phone number: " + customer.getPhonenumber());
        System.out.println("Address: " + customer.getAddress());
        System.out.println();
        System.out.println("Enter anything to return.");
    }

    @Override
    public Action getNextAction() {
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();

        return Action.ACCOUNT;
    }
}
