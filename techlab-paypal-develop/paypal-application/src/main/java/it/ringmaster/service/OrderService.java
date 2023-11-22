package it.ringmaster.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
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
import java.util.Map;


@Slf4j
@Service
public class OrderService {

    @Value("${paypal.clientId}")
    private String clientId;

    @Value("${paypal.secret}")
    private String secret;

    private static final String PAYPAL_API_URL = "https://api-m.sandbox.paypal.com/v1/payments/payment";

    public String confirmOrder(String id) {
        ResponseEntity<String> response = this.confirmById(id);
//        return parseStatus(response.getBody());
        return null;
    }

    public ResponseEntity<String> executeOrder(String id, String json) {
        return this.executeById(id, json);
    }

    public ResponseEntity<String> retrieveOrder(String id) {
        return this.retrieveById(id);
    }

    public ResponseEntity<String> createOrder(String json) {
        return this.makePaymentPost(json);
    }


    private ResponseEntity<String> confirmById(String id) {
        HttpClient httpClient = HttpClient.newHttpClient();

        // payload should be corrected
        String payload = "{ \"payment_source\": { \"paypal\": { \"name\": { \"given_name\": \"John\"," +
                " \"surname\": \"Doe\" }, \"email_address\": \"customer@example.com\", " +
                "\"experience_context\": { \"payment_method_preference\": \"IMMEDIATE_PAYMENT_REQUIRED\", " +
                "\"brand_name\": \"EXAMPLE INC\", \"locale\": \"en-US\", \"landing_page\": \"LOGIN\", " +
                "\"shipping_preference\": \"SET_PROVIDED_ADDRESS\", \"user_action\": \"PAY_NOW\", " +
                "\"return_url\": \"https://example.com/returnUrl\", \"cancel_url\": \"https://example.com/cancelUrl\" } } } }";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(PAYPAL_API_URL + "/" + id + "/confirm-payment-source"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Basic " + Base64.getEncoder().encodeToString((clientId + ":" + secret).getBytes()))
                .POST(HttpRequest.BodyPublishers.ofString(payload))
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 201) {
                // Parse the JSON response
                return ResponseEntity.status(response.statusCode()).body(response.body());
            } else {
                // Handle error response
                return ResponseEntity.status(response.statusCode()).body("Error getting creating order");
            }
        } catch (IOException | InterruptedException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error making PayPal API request");
        }
    }

    private ResponseEntity<String> executeById(String id, String json) {
        HttpClient httpClient = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(PAYPAL_API_URL + "/" + id + "/execute"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Basic " + Base64.getEncoder().encodeToString((clientId + ":" + secret).getBytes()))
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            return ResponseEntity.status(response.statusCode()).body(response.body());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error making PayPal API request");
        }
    }

    private ResponseEntity<String> retrieveById(String id) {
        HttpClient httpClient = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(PAYPAL_API_URL + "/" + id))
                .header("Content-Type", "application/json")
                .header("Authorization", "Basic " + Base64.getEncoder().encodeToString((clientId + ":" + secret).getBytes()))
                .GET()
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            return ResponseEntity.status(response.statusCode()).body(response.body());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error making PayPal API request");
        }
    }


    private ResponseEntity<String> makePaymentPost(String jsonPayload)  {
        HttpClient httpClient = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(PAYPAL_API_URL))
                .header("Content-Type", "application/json")
                .header("Authorization", "Basic " + Base64.getEncoder().encodeToString((clientId + ":" + secret).getBytes()))
                .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            return ResponseEntity.status(response.statusCode()).body(response.body());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error making PayPal API request");
        }
    }


}
