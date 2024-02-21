package pl.kul.kulzaki.Screens;

import pl.kul.kulzaki.Action;
import pl.kul.kulzaki.Config;
import pl.kul.kulzaki.Screen;
import pl.kul.kulzaki.Store.CustomerService;
import pl.kul.kulzaki.Util.HttpUtil;
import pl.kul.kulzaki.Util.JsonUtil;

import java.net.http.HttpResponse;
import java.util.Scanner;

public class LoginScreen implements Screen {

    private final CustomerService customerService;

    private String email;
    private String password;

    public LoginScreen(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    public void process() {
        System.out.println(">> User authorization <<");
        System.out.println("Enter your account credentials.");
        this.handleEmailInput();
        this.handlePasswordInput();
    }

    private void handlePasswordInput() {
        do {
            System.out.print("password: ");
            Scanner scanner = new Scanner(System.in);
            this.password = scanner.nextLine();

            System.out.println();
        } while(!validatePassword(password));
    }

    private void handleEmailInput() {
        do {
            System.out.print("e-mail: ");
            Scanner scanner = new Scanner(System.in);
            this.email = scanner.nextLine();
        } while(!validateEmail(email));
    }

    private boolean validateEmail(String email) {
        // TODO
        return true;
    }

    private boolean validatePassword(String password) {
        // TODO
        return true;
    }

    @Override
    public Action getNextAction() {
        String url = Config.HOST + "/auth/login";
        String jsonBody = "{\"email\":\"" + this.email + "\", \"password\":\"" + this.password + "\"}";

        try {
            System.out.println("Logging in...");
            HttpResponse<String> response = HttpUtil.sendPostRequest(url, jsonBody);

            if (response.statusCode() == 200) {
                String jwtToken = JsonUtil.getJWTFromJson(response.body());

                this.customerService.setJWT(jwtToken);
                return Action.ACCOUNT;
            } else {
                System.out.println("Login failed. Status code: " + response.statusCode());
                return Action.WELCOME;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Action.WELCOME;
        }
    }
}
