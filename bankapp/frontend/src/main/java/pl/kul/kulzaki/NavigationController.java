package pl.kul.kulzaki;

import lombok.Getter;
import pl.kul.kulzaki.Screens.*;
import pl.kul.kulzaki.Store.CustomerService;

@Getter
public class NavigationController {
    private Screen currentScreen;
    private final CustomerService customerService = new CustomerService();

    public NavigationController() {
        this.currentScreen = new WelcomeScreen(customerService);
    }

    public void start() {
        while(true) {
            this.displayDivider();
            currentScreen.process();
            Action nextAction = currentScreen.getNextAction();
            handleAction(nextAction);

            if (nextAction == Action.EXIT)
                return;
        }
    }

    public void handleAction(Action nextAction) {
        if (nextAction == Action.INVALID_ACTION) {
            System.out.println("Invalid input. Try again.");
            return;
        }

        switch(nextAction) {
            case WELCOME:
                this.currentScreen = new WelcomeScreen(customerService);
                break;
            case LOGIN:
                this.currentScreen = new LoginScreen(customerService);
                break;
            case REGISTER:
                this.currentScreen = new RegisterScreen(customerService);
                break;
            case ACCOUNT:
                this.currentScreen = new AccountScreen(customerService);
                break;
            case ACCOUNT_DETAILS:
                this.currentScreen = new DetailsScreen(customerService);
                break;
            case HISTORY:
                this.currentScreen = new HistoryScreen(customerService);
                break;
            case TRANSFER:
                this.currentScreen = new TransferScreen(customerService);
                break;
            case LOGOUT:
                this.currentScreen = new LogoutScreen(customerService);
                break;
        }
    }

    private void displayDivider() {
        System.out.println();
        System.out.println("#######################");
        System.out.println();
    }
}
