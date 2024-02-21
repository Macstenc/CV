package pl.kul.kulzaki;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.kul.kulzaki.Screens.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class NavigationControllerTest {

    private NavigationController navigationController;

    @BeforeEach
    public void setUp() {
        navigationController = new NavigationController();
    }

    @Test
    public void shouldSwitchToRegisterScreenWhenActionIsRegister() {
        navigationController.handleAction(Action.REGISTER);
        assertTrue(navigationController.getCurrentScreen() instanceof RegisterScreen);
    }

    @Test
    public void shouldSwitchToAccountScreenWhenActionIsAccount() {
        navigationController.handleAction(Action.ACCOUNT);
        assertTrue(navigationController.getCurrentScreen() instanceof AccountScreen);
    }

    @Test
    public void shouldSwitchToAccountDetailsScreenWhenActionIsAccountDetails() {
        navigationController.handleAction(Action.ACCOUNT_DETAILS);
        assertTrue(navigationController.getCurrentScreen() instanceof DetailsScreen);
    }

    @Test
    public void shouldSwitchToHistoryScreenWhenActionIsHistory() {
        navigationController.handleAction(Action.HISTORY);
        assertTrue(navigationController.getCurrentScreen() instanceof HistoryScreen);
    }

    @Test
    public void shouldSwitchToTransferScreenWhenActionIsTransfer() {
        navigationController.handleAction(Action.TRANSFER);
        assertTrue(navigationController.getCurrentScreen() instanceof TransferScreen);
    }

    @Test
    public void shouldSwitchToLogoutScreenWhenActionIsLogout() {
        navigationController.handleAction(Action.LOGOUT);
        assertTrue(navigationController.getCurrentScreen() instanceof LogoutScreen);
    }
}
