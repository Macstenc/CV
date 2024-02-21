package pl.kul.kulzaki.Screens;

import pl.kul.kulzaki.Action;
import pl.kul.kulzaki.Screen;
import pl.kul.kulzaki.Store.CustomerService;

import java.util.Scanner;

public class WelcomeScreen implements Screen {

    private final CustomerService customerService;

    public WelcomeScreen(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    public void process() {
        System.out.println("Welcome to");
        System.out.println("        _  ___   _ _");
        System.out.println("  _ __ | |/ / | | | |   ");
        System.out.println(" | '  \\| ' <| |_| | |__");
        System.out.println(" |_|_|_|_|\\_\\\\___/|____|");
        System.out.println("    ->>  The future of banking  <<-");
        System.out.println();
        System.out.println("Action:");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println();
        System.out.println("0. Exit");
        System.out.println();
    }

    @Override
    public Action getNextAction() {
        System.out.print("> ");

        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();

        switch (choice) {
            case 0:
                return Action.EXIT;
            case 1:
                return Action.LOGIN;
            case 2:
                return Action.REGISTER;
        }

        return Action.INVALID_ACTION;
    }
}
