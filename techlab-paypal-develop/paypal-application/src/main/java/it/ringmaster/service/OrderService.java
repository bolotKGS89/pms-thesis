package it.ringmaster.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;


@Slf4j
@Service
public class OrderService {

    @Value("${paypal.clientId}")
    private String clientId;

    @Value("${paypal.secret}")
    private String secret;

    private static final String PAYPAL_API_URL = "https://api-m.sandbox.paypal.com/v2/checkout/orders";

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private Paypal;

    public String confirmOrder(String id) {
        ResponseEntity<String> response = this.confirmById(id);
        return parseStatus(response.getBody());
    }

    public String captureOrder(String id) {
        ResponseEntity<String> response = this.captureById(id);
        return parseStatus(response.getBody());
    }

    public String retrieveOrder(String id) {
        ResponseEntity<String> response = this.retrieveById(id);
        return parseStatus(response.getBody());
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

    private ResponseEntity<String> captureById(String id) {
        HttpClient httpClient = HttpClient.newHttpClient();

        // payload should be corrected
        String payload = "{ payment_source\": {\n" +
                "\"paypal\": { }\n" +
                "}}";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(PAYPAL_API_URL + "/" + id + "/capture"))
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

            if (response.statusCode() == 200) {
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

    public String createOrder() {
        ResponseEntity<String> response = this.makePaymentPost();
        System.out.println(response.getBody());
        return parseStatus(response.getBody());
    }

    private ResponseEntity<String> makePaymentPost()  {
        HttpClient httpClient = HttpClient.newHttpClient();
        // Construct PayPal API request payload (customize as needed)
        String orderPayload = "\"{ \\\"intent\\\": \\\"CAPTURE\\\", \\\"purchase_units\\\":" +
                " [ { \\\"reference_id\\\": \\\"d9f80740-38f0-11e8-b467-0ed5f89f718b\\\", " +
                "\\\"amount\\\": { \\\"currency_code\\\": \\\"USD\\\", \\\"value\\\": \\\"100.00\\\" } } ]," +
                " \\\"payment_source\\\": { \\\"paypal\\\": { \\\"experience_context\\\": { \\\"payment_method_preference\\\": \\\"IMMEDIATE_PAYMENT_REQUIRED\\\", \\\"brand_name\\\": \\\"EXAMPLE INC\\\", \\\"locale\\\": \\\"en-US\\\", \\\"landing_page\\\": \\\"LOGIN\\\", \\\"shipping_preference\\\": \\\"SET_PROVIDED_ADDRESS\\\", \\\"user_action\\\": \\\"PAY_NOW\\\", \\\"return_url\\\": \\\"https://example.com/returnUrl\\\", \\\"cancel_url\\\": \\\"https://example.com/cancelUrl\\\" } } } }\"";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(PAYPAL_API_URL))
                .header("Content-Type", "application/json")
                .header("Authorization", "Basic " + Base64.getEncoder().encodeToString((clientId + ":" + secret).getBytes()))
                .POST(HttpRequest.BodyPublishers.ofString(orderPayload))
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
//            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error making PayPal API request");
        }
    }

    private String parseStatus(String responseBody) {
        try {
            // Parse the JSON response
            JsonNode jsonNode = objectMapper.readTree(responseBody);

            // Extract the access token field
            String status = jsonNode.get("status").asText();
            String id = jsonNode.get("id").asText();


            // Create a new JSON object
            ObjectNode outputJson = JsonNodeFactory.instance.objectNode();
            outputJson.put("status", status);
            outputJson.put("id", id);

            // Convert the ObjectNode to a JSON String
            return outputJson.toString();
        } catch (Exception e) {
            // Handle JSON parsing exception
            System.err.println("Error parsing JSON response: " + e.getMessage());
            return null;
        }
    }
}
