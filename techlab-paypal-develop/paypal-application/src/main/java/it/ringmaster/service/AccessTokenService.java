package it.ringmaster.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;

@Slf4j
@Service
public class AccessTokenService {

    @Value("${paypal.clientId}")
    private String clientId;

    @Value("${paypal.secret}")
    private String secret;

    private static final String PAYPAL_ACCESS_TOKEN_URL = "https://api-m.sandbox.paypal.com/v1/oauth2/token";

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public String getAccessToken() {
        ResponseEntity<String> response = this.postAccessToken();
        return parseAccessToken(response.getBody());
    }

    private ResponseEntity<String> postAccessToken() {
        HttpClient httpClient = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(PAYPAL_ACCESS_TOKEN_URL))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("Authorization", "Basic " + Base64.getEncoder().encodeToString((clientId + ":" + secret).getBytes()))
                .POST(HttpRequest.BodyPublishers.ofString("grant_type=client_credentials"))
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                // Parse the JSON response
                return ResponseEntity.status(response.statusCode()).body(response.body());
            } else {
                // Handle error response
                return ResponseEntity.status(response.statusCode()).body("Error getting access token");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error making PayPal API request");
        }

    }

    private String parseAccessToken(String responseBody) {
        try {
            // Parse the JSON response
            JsonNode jsonNode = objectMapper.readTree(responseBody);

            // Extract the access token field
            JsonNode accessTokenNode = jsonNode.get("access_token");

            if (accessTokenNode != null) {
                return accessTokenNode.asText();
            } else {
                // Handle the case where the access token is not present in the response
                System.err.println("Access token not found in the response.");
                return null;
            }
        } catch (Exception e) {
            // Handle JSON parsing exception
            System.err.println("Error parsing JSON response: " + e.getMessage());
            return null;
        }
    }
}
