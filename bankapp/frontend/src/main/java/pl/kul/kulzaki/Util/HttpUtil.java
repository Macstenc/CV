package pl.kul.kulzaki.Util;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;

public class HttpUtil {

    private static final HttpClient httpClient = HttpClient.newHttpClient();

    public static HttpResponse<String> sendPostRequestWithToken(String url, String jsonBody, String jwtToken) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + jwtToken)
                .POST(BodyPublishers.ofString(jsonBody))
                .build();

        return httpClient.send(request, BodyHandlers.ofString());
    }

    public static HttpResponse<String> sendGetRequestWithToken(String url, String jwtToken) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + jwtToken)
                .GET()
                .build();

        return httpClient.send(request, BodyHandlers.ofString());
    }

    public static HttpResponse<String> sendPostRequest(String url, String jsonBody) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(BodyPublishers.ofString(jsonBody))
                .build();

        return httpClient.send(request, BodyHandlers.ofString());
    }
}
