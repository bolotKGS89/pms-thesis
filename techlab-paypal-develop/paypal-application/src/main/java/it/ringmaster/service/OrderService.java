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
            log.info("Receiving request (voidPayment) from {} (request_id {})", serviceName, requestId);
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            log.info("Responding to request (voidPayment) from {} (request_id {})", "techlab-paypal-develop", requestId);

            return ResponseEntity.status( HttpStatus.CREATED).body(response.body());
        } catch (IOException | InterruptedException e) {
            log.error("Error response (voidPayment) (code: {}) received from {} (request_id {})", e.getCause(), serviceName, requestId);
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error making PayPal API request");
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
            log.info("Receiving request (authorize) from {} (request_id {})", serviceName, requestId);
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            log.info("Responding to request (authorize) from {} (request_id {})", "techlab-paypal-develop", requestId);

            return ResponseEntity.status( HttpStatus.CREATED).body(response.body());
        } catch (IOException | InterruptedException e) {
            log.error("Error response (authorize) (code: {}) received from {} (request_id {})", e.getCause(), serviceName, requestId);
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error making PayPal API request");
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
            log.info("Receiving request (capture) from {} (request_id {})", serviceName, requestId);
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            log.info("Responding to request (capture) from {} (request_id {})", "techlab-paypal-develop", requestId);

            return ResponseEntity.status( HttpStatus.CREATED).body(response.body());
        } catch (IOException | InterruptedException e) {
            log.error("Error response (capture) (code: {}) received from {} (request_id {})", e.getCause(), serviceName, requestId);
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error making PayPal API request");
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
            log.info("Receiving request (retrieveById) from {} (request_id {})", serviceName, requestId);
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            log.info("Responding to request (retrieveById) from {} (request_id {})", "techlab-paypal-develop", requestId);

            return ResponseEntity.status( HttpStatus.CREATED).body(response.body());
        } catch (IOException | InterruptedException e) {
            log.error("Error response (retrieveById) (code: {}) received from {} (request_id {})", e.getCause(), serviceName, requestId);
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error making PayPal API request");
        }
    }

    private ResponseEntity<String> makePaymentPost(String jsonPayload, String serviceName, String requestId)  {
        HttpClient httpClient = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(PAYPAL_API_URL))
                .header("Content-Type", "application/json")
                .header("Authorization", "Basic " + Base64.getEncoder().encodeToString((clientId + ":" + secret).getBytes()))
                .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                .build();

        try {
            log.info("Receiving request (makePaymentPost) from {} (request_id {})", serviceName, requestId);
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            log.info("Responding to request (makePaymentPost) from {} (request_id {})", "techlab-paypal-develop", requestId);

            return ResponseEntity.status( HttpStatus.CREATED).body(response.body());
        } catch (IOException | InterruptedException e) {
            log.error("Error response (makePaymentPost) (code: {}) received from {} (request_id {})", e.getCause(), serviceName, requestId);
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error making PayPal API request");
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
            log.info("Receiving request (makeRefund) from {} (request_id {})", serviceName, requestId);
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            log.info("Responding to request (makeRefund) from {} (request_id {})", "techlab-paypal-develop", requestId);
            return ResponseEntity.status( HttpStatus.CREATED).body(response.body());
        } catch (IOException | InterruptedException e) {
            log.error("Error response (makeRefund) (code: {}) received from {} (request_id {})", e.getCause(), serviceName, requestId);
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error making PayPal API request");
        }
    }


}
