package pl.kul.kulzaki.Store;

import lombok.Data;
import org.json.JSONObject;
import pl.kul.kulzaki.Config;
import pl.kul.kulzaki.Dto.CheckAccountExistResponse;
import pl.kul.kulzaki.Models.Customer;
import pl.kul.kulzaki.Util.HttpUtil;
import pl.kul.kulzaki.Util.JsonUtil;

import java.net.http.HttpResponse;

@Data
public class CustomerService {
    private String JWT;
    private Customer customer;

    public void fetchCustomer() {
        System.out.println("loading...");
        try {
            HttpResponse<String> response = HttpUtil.sendGetRequestWithToken(Config.HOST + "/customers/getCustomer", this.JWT);

            if (response.statusCode() == 200) {
                JSONObject jsonResponse = new JSONObject(response.body());
                this.customer = JsonUtil.jsonToCustomer(jsonResponse);
                System.out.println("account updated");
                System.out.println();
            } else {
                System.out.println("failed! Status code: " + response.statusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("error!");
        }
    }

    public void sendTransaction(String to, int amount) {
        String url = Config.HOST + "/customers/transaction";
        String jsonBody = "{\"customerfrom\":\"" + this.customer.getCardNumber() + "\", \"customerto\":\"" + to + "\", \"amount\": " + amount +"}";

        try {
            HttpUtil.sendPostRequestWithToken(url, jsonBody, JWT);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public CheckAccountExistResponse checkAccountExist(String to) {
        String url = Config.HOST + "/customers/checkAccountExist";
        String jsonBody = "{\"customerto\":\"" + to + "\"}";

        try {
            HttpResponse<String> response = HttpUtil.sendPostRequestWithToken(url, jsonBody, JWT);

            if (response.statusCode() == 200) {
                JSONObject jsonResponse = new JSONObject(response.body());

                return new CheckAccountExistResponse(
                        jsonResponse.getBoolean("exist"),
                        jsonResponse.getString("message")
                );
            } else {
                return null;
            }
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
            return null;
        }
    }
}
