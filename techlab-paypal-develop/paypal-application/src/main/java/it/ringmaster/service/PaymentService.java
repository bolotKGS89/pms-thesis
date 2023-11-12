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

@Slf4j
@Service
public class PaymentService {

    @Value("${paypal.clientId}")
    private String clientId;

    @Value("${paypal.secret}")
    private String secret;

    private static final String PAYPAL_API_URL = "https://api-m.sandbox.paypal.com/v2/payments";

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public String retrievePayment(String id) {
        ResponseEntity<String> response = this.retrieveById(id);
        return parseStatus(response.getBody());
    }

    public String capturePayment(String id) {
        ResponseEntity<String> response = this.captureById(id);
        return parseStatus(response.getBody());
    }

    private ResponseEntity<String> captureById(String id) {
        HttpClient httpClient = HttpClient.newHttpClient();

        String payload = "{ \"amount\": { \"value\": \"10.99\", \"currency_code\": \"USD\" }, " +
                "\"invoice_id\": \"INVOICE-123\", \"final_capture\": true, " +
                "\"note_to_payer\": \"If the ordered color is not available, we will substitute with a different color free of charge.\", " +
                "\"soft_descriptor\": \"Bobs";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(PAYPAL_API_URL + "/authorizations/" + id + "/capture"))
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
                return ResponseEntity.status(response.statusCode()).body("Error getting creating payment");
            }
        } catch (IOException | InterruptedException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error making PayPal API request");
        }
    }

    private ResponseEntity<String> retrieveById(String id) {
        HttpClient httpClient = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(PAYPAL_API_URL + "/authorizations/" + id))
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
