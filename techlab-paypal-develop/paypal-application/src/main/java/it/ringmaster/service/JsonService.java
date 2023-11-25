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

    public String createRefund(Double total, String currency, String note) {
        JsonObject refundObject = Json.createObjectBuilder()
                .add("amount", Json.createObjectBuilder()
                        .add("value", total.toString())
                        .add("currency_code", currency))
                .add("invoice_id", String.valueOf(System.currentTimeMillis()))
                .add("note_to_payer", note)
                .build();

        return refundObject.toString();
    }

     public String export(String paymentIntent, Double total, String currency) {
        JsonObject empObject = Json.createObjectBuilder()
                .add("intent", paymentIntent)
                .add("purchase_units",
                    Json.createArrayBuilder().add(
                        Json.createObjectBuilder().add("items",
                            Json.createArrayBuilder().add(
                                 Json.createObjectBuilder()
                                     .add("name", "T-shirt")
                                     .add("description", "Green XL")
                                     .add("quantity", "1")
                                         .add("unit_amount", Json.createObjectBuilder()
                                                 .add("currency_code", currency)
                                                 .add("value", total.toString()))

                            )
                        ).add("amount",
                                Json.createObjectBuilder()
                                        .add("currency_code", currency)
                                        .add("value", total.toString())
                                        .add("breakdown", Json.createObjectBuilder().add("item_total",
                                                Json.createObjectBuilder().add("currency_code", currency)
                                                        .add("value", total.toString())))
                        )
                    )
                )
                .add("application_context", Json.createObjectBuilder()
                        .add("return_url", "https://example.com/return")
                        .add("cancel_url", "https://example.com/cancel"))
                .build();
        return empObject.toString();
    }
}
