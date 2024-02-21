package pl.kul.kulzaki.Screens;

import pl.kul.kulzaki.Action;
import pl.kul.kulzaki.Config;
import pl.kul.kulzaki.Models.Transaction;
import pl.kul.kulzaki.Screen;
import pl.kul.kulzaki.Store.CustomerService;

import java.util.List;
import java.util.Scanner;

public class HistoryScreen implements Screen {

    private final CustomerService customerService;

    public HistoryScreen(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    public void process() {
        List<Transaction> transactions = customerService.getCustomer().getTransactions();

        System.out.println("Transaction history:");
        System.out.println("------------------------");
        for (int i = 0; i< 5 && transactions.size() > i; i++) {
            displayTransfer(transactions.get(i));
            System.out.println("------------------------");
        }
        System.out.println();
        System.out.println("Enter anything to return.");
    }

    private void displayTransfer(Transaction transaction) {
        float amountFloat = ((float) transaction.getAmount()) / Config.DECIMAL_PRECISION;
        String from = transaction.getSenderAccount();
        String to = transaction.getReceiverAccount();

        if (from.equals(customerService.getCustomer().getCardNumber()))
            from = "you";

        if (to.equals(customerService.getCustomer().getCardNumber()))
            to = "you";


        System.out.println("| "+ from +" --> "+ to +" |");
        System.out.println("| " + amountFloat + " usd");
    }

    @Override
    public Action getNextAction() {
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();

        return Action.ACCOUNT;
    }
}
