package pl.kul.kulzaki.Screens;

import pl.kul.kulzaki.Action;
import pl.kul.kulzaki.Config;
import pl.kul.kulzaki.Dto.CheckAccountExistResponse;
import pl.kul.kulzaki.Screen;
import pl.kul.kulzaki.Store.CustomerService;

import java.util.Scanner;

public class TransferScreen implements Screen {

    private final CustomerService customerService;

    private String destination;
    private int amount;

    public TransferScreen(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    public void process() {
        System.out.println(">> New transfer <<");
        System.out.println("Enter destination account number and amount.");

        handleDestinationInput();
        handleAmountInput();

        customerService.sendTransaction(this.destination, this.amount);
    }

    private void handleDestinationInput() {
        CheckAccountExistResponse accountExistResponse;
        boolean confirmed = false;
        do {
            System.out.print("to: ");
            Scanner scanner = new Scanner(System.in);
            this.destination = scanner.nextLine();

            accountExistResponse = customerService.checkAccountExist(this.destination);

            System.out.println(accountExistResponse.message);
            System.out.println();
            System.out.print("(y/n) > ");

            if (accountExistResponse.exist) {
                String confirmation = scanner.nextLine();
                if (confirmation.equalsIgnoreCase("y") || confirmation.equalsIgnoreCase("yes")) {
                    confirmed = true;
                }
            }

        } while(this.destination.isEmpty() || accountExistResponse == null || !confirmed);
    }

    private void handleAmountInput() {
        do {
            System.out.print("amount: ");
            Scanner scanner = new Scanner(System.in);
            float input = scanner.nextFloat();
            this.amount = (int) (input * Config.DECIMAL_PRECISION);

            System.out.println();

            if (this.amount <= 0)
                System.out.println("Amount must be greater than 0.");
        } while(this.amount <= 0);
    }

    @Override
    public Action getNextAction() {
        return Action.ACCOUNT;
    }
}
