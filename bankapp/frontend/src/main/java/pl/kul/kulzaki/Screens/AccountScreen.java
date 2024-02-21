package pl.kul.kulzaki.Screens;

import pl.kul.kulzaki.Action;
import pl.kul.kulzaki.Config;
import pl.kul.kulzaki.Models.Customer;
import pl.kul.kulzaki.Screen;
import pl.kul.kulzaki.Store.CustomerService;

import java.util.Scanner;

public class AccountScreen implements Screen {

    private final CustomerService customerService;

    public AccountScreen(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    public void process() {
        this.customerService.fetchCustomer();
        if (customerService.getCustomer() == null)
            return;

        Customer customer = customerService.getCustomer();

        float balanceFloat = ((float) customer.getBalance()) / Config.DECIMAL_PRECISION;

        System.out.println("Your account:");
        System.out.println("E-mail: " + customer.getEmail());
        System.out.println("Card number: " + customer.getCardNumber());
        System.out.println("Balance: " + balanceFloat + " usd");
        System.out.println();
        System.out.println("Action:");
        System.out.println("1. New transfer");
        System.out.println("2. History");
        System.out.println("3. Account details");
        System.out.println();
        System.out.println("0. Logout");
        System.out.println();
    }

    @Override
    public Action getNextAction() {
        System.out.print("> ");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                return Action.TRANSFER;
            case 2:
                return Action.HISTORY;
            case 3:
                return Action.ACCOUNT_DETAILS;
            case 0:
                return Action.LOGOUT;
        }

        return Action.INVALID_ACTION;
    }
}
