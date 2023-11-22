package it.ringmaster.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Service;

import javax.json.*;

@Service
public class JsonService {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public String getPaymentId(String payerId) {
        return Json.createObjectBuilder()
                .add("payer_id", payerId)
                .build().toString();
    }

    public String export(String paymentIntent, Double total, String currency) {
        JsonObject empObject = Json.createObjectBuilder()
                .add("intent", paymentIntent)
                .add("payer", Json.createObjectBuilder().add("payment_method", "paypal"))
                .add("transactions",
                        Json.createArrayBuilder().add(
                                Json.createObjectBuilder().add("amount",
                                        Json.createObjectBuilder()
                                        .add("total", total.toString())
                                        .add("currency", currency)
                                )
                        )
                )
                .add("redirect_urls", Json.createObjectBuilder()
                        .add("return_url", "https://example.com/return")
                        .add("cancel_url", "https://example.com/cancel"))
                .build();
        return empObject.toString();
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
