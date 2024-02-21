package pl.kul.kulzaki.Screens;

import pl.kul.kulzaki.Action;
import pl.kul.kulzaki.Screen;
import pl.kul.kulzaki.Store.CustomerService;

import java.util.Scanner;

public class LogoutScreen implements Screen {

    private final CustomerService customerService;

    public LogoutScreen(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    public void process() {
        System.out.println("User logged out.");
    }

    @Override
    public Action getNextAction() {
        customerService.setJWT(null);
        customerService.setCustomer(null);
        return Action.WELCOME;
    }
}
