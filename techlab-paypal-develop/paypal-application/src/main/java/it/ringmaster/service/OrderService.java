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

    private static final String PAYPAL_API_URL = "https://api-m.sandbox.paypal.com/v2/checkout/orders";

    private static final String PAYPAL_API_URL_PAYMENT = "https://api-m.sandbox.paypal.com/v2/payments/captures";


    public ResponseEntity<String> captureOrder(String id) {
        return this.capture(id);
    }

    public ResponseEntity<String> retrieveOrder(String id) {
        return this.retrieveById(id);
    }

    public ResponseEntity<String> createOrder(String json) {
        return this.makePaymentPost(json);
    }

    public ResponseEntity<String> refundPayment(String id, String json) {
        return this.refundPayment(id, json);
    }

    private ResponseEntity<String> capture(String id) {
        HttpClient httpClient = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(PAYPAL_API_URL + "/" + id + "/capture"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Basic " + Base64.getEncoder().encodeToString((clientId + ":" + secret).getBytes()))
                .POST(HttpRequest.BodyPublishers.noBody())
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

    private ResponseEntity<String> makeRefund(String id, String jsonPayload)  {
        HttpClient httpClient = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(PAYPAL_API_URL_PAYMENT + "/" + id + "/refund"))
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
