package it.ringmaster.service;

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
import java.util.HashMap;
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

    private static final String PAYPAL_API_AUTHORIZE_URL = "https://api-m.sandbox.paypal.com/v2/payments/authorizations";

    public ResponseEntity<String> authorizeOrder(String id, String serviceName, String requestId) {
        return this.authorize(id, serviceName, requestId);
    }

    public ResponseEntity<String> captureOrder(String id, String serviceName, String requestId) {
        return this.capture(id, serviceName, requestId);
    }

    public ResponseEntity<String> retrieveOrder(String id, String serviceName, String requestId) {
        return this.retrieveById(id, serviceName, requestId);
    }

    public ResponseEntity<String> createOrder(String json, String serviceName, String requestId) {
        return this.makePaymentPost(json, serviceName, requestId);
    }

    public ResponseEntity<String> refundPayment(String id, String json, String serviceName, String requestId) {
        return this.makeRefund(id, json, serviceName, requestId);
    }

    public ResponseEntity<String> voidPayment(String id, String serviceName, String requestId) {
        HttpClient httpClient = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(PAYPAL_API_AUTHORIZE_URL + "/" + id + "/void"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Basic " + Base64.getEncoder().encodeToString((clientId + ":" + secret).getBytes()))
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();

        try {
            log.info("Received GET request from {} (request_id {})", "127.0.0.1", requestId);
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            log.info("Answered to GET request from {} with code: {} (request_id {})", "127.0.0.1", "200", requestId);
            return ResponseEntity.status( HttpStatus.CREATED).body(response.body());
        } catch (IOException | InterruptedException e) {
            log.error("Answered to GET request from {} with code: {} (request_id {})", "127.0.0.1", "500", requestId);
            e.printStackTrace();
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("serviceName", "techlab_paypal");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse.toString());
        }
    }

    private ResponseEntity<String> authorize(String id, String serviceName, String requestId) {
        HttpClient httpClient = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(PAYPAL_API_URL + "/" + id + "/authorize"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Basic " + Base64.getEncoder().encodeToString((clientId + ":" + secret).getBytes()))
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();

        try {
            log.info("Received POST request from {} (request_id {})", "127.0.0.1", requestId);
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            log.info("Answered to POST request from {} with code: {} (request_id {})", "127.0.0.1", "200", requestId);
            return ResponseEntity.status( HttpStatus.CREATED).body(response.body());
        } catch (IOException | InterruptedException e) {
            log.error("Answered to POST request from {} with code: {} (request_id {})", "127.0.0.1", "500", requestId);
            e.printStackTrace();
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("serviceName", "techlab_paypal");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse.toString());
        }
    }

    private ResponseEntity<String> capture(String id, String serviceName, String requestId) {
        HttpClient httpClient = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(PAYPAL_API_AUTHORIZE_URL + "/" + id + "/capture"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Basic " + Base64.getEncoder().encodeToString((clientId + ":" + secret).getBytes()))
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();

        try {
            log.info("Received POST request from {} (request_id {})", "127.0.0.1", requestId);
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            log.info("Answered to POST request from {} with code: {} (request_id {})", "127.0.0.1", "200", requestId);
            return ResponseEntity.status( HttpStatus.CREATED).body(response.body());
        } catch (IOException | InterruptedException e) {
            log.error("Answered to POST request from {} with code: {} (request_id {})", "127.0.0.1", "500", requestId);
            e.printStackTrace();
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("serviceName", "techlab_paypal");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse.toString());
        }
    }

    private ResponseEntity<String> retrieveById(String id, String serviceName, String requestId) {
        HttpClient httpClient = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(PAYPAL_API_URL + "/" + id))
                .header("Content-Type", "application/json")
                .header("Authorization", "Basic " + Base64.getEncoder().encodeToString((clientId + ":" + secret).getBytes()))
                .GET()
                .build();

        try {
            log.info("Received GET request from {} (request_id {})", "127.0.0.1", requestId);
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            log.info("Answered to GET request from {} with code: {} (request_id {})", "127.0.0.1", "200", requestId);
            return ResponseEntity.status( HttpStatus.CREATED).body(response.body());
        } catch (IOException | InterruptedException e) {
            log.error("Answered to GET request from {} with code: {} (request_id {})", "127.0.0.1", "500", requestId);
            e.printStackTrace();
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("serviceName", "techlab_paypal");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse.toString());
        }
    }

    private ResponseEntity<String> makePaymentPost(String jsonPayload, String serviceName, String requestId)  {
        HttpClient httpClient = HttpClient.newHttpClient();

        try {
            log.info("Received POST request from {} (request_id: {})", serviceName, requestId);
            if (serviceName.equals("paypal")) {
                log.error("paypal failed");
                throw new Exception();
            }

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(PAYPAL_API_URL))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Basic " + Base64.getEncoder().encodeToString((clientId + ":" + secret).getBytes()))
                    .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            log.info("Answered to POST request from {} with code: {} (request_id: {})", serviceName, "200", requestId);
            return ResponseEntity.status( HttpStatus.CREATED).body(response.body());
        } catch (Exception e) {
            log.error("Answered to POST request from {} with code: {} (request_id: {})", serviceName, "500", requestId);
            e.printStackTrace();
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("serviceName", "paypal");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse.toString());
        }
    }

    private ResponseEntity<String> makeRefund(String id, String jsonPayload, String serviceName, String requestId)  {
        HttpClient httpClient = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(PAYPAL_API_URL_PAYMENT + "/" + id + "/refund"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Basic " + Base64.getEncoder().encodeToString((clientId + ":" + secret).getBytes()))
                .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                .build();

        try {
            log.info("Received POST request from {} (request_id {})", "127.0.0.1", requestId);
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            log.info("Answered to POST request from {} with code: {} (request_id {})", "127.0.0.1", "200", requestId);
            return ResponseEntity.status( HttpStatus.CREATED).body(response.body());
        } catch (IOException | InterruptedException e) {
            log.error("Answered to POST request from {} with code: {} (request_id {})", "127.0.0.1", "500", requestId);
            e.printStackTrace();
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("serviceName", "techlab_paypal");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse.toString());
        }
    }


}
