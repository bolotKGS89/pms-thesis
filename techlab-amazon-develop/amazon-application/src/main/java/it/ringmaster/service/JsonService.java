package it.ringmaster.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.Currency;

@Service
public class JsonService {
    private static final String CHECKOUT_URL = "https://www.reply.com/it";
    private static final String STORE_ID = "amzn1.application-oa2-client.746614401d0941c5abf825ab9bdf8c6f";

    public JSONObject getPayLoadForCreateCheckout() {
        JSONObject payload = new JSONObject();
        JSONObject webCheckoutDetails = new JSONObject();
        webCheckoutDetails.put("checkoutReviewReturnUrl", CHECKOUT_URL);
        webCheckoutDetails.put("checkoutResultReturnUrl", CHECKOUT_URL);

        JSONObject paymentDetails = new JSONObject();
        paymentDetails.put("paymentIntent", "Authorize");

        JSONObject recurringMetadata = new JSONObject();
        recurringMetadata.put("frequency", new JSONObject()
                .put("unit", "Variable")
                .put("value", "0"));

        payload.put("webCheckoutDetails", webCheckoutDetails);
        payload.put("storeId", STORE_ID);
        payload.put("paymentDetails", paymentDetails);
        payload.put("chargePermissionType", "Recurring");
        payload.put("recurringMetadata", recurringMetadata);
        payload.put("scopes", new JSONArray()
                                .put("name")
                .put("email")
                .put("phoneNumber")
                .put("billingAddress"));


        return payload;
    }

    public JSONObject getPayLoadForCompleteCheckout(Double amount, String currency) {
        JSONObject payload = new JSONObject();
        JSONObject chargeAmount = new JSONObject();
        chargeAmount.put("amount", amount.toString());
        chargeAmount.put("currencyCode", currency);
        payload.put("chargeAmount", chargeAmount);

        return payload;
    }

    public JSONObject updatePayload(Double amount, String currency) {
        JSONObject payload = new JSONObject();
        JSONObject updateWebCheckoutDetails = new JSONObject();
        updateWebCheckoutDetails.put("checkoutResultReturnUrl", CHECKOUT_URL);
        payload.put("webCheckoutDetails", updateWebCheckoutDetails);

        JSONObject paymentDetails = new JSONObject();
        paymentDetails.put("paymentIntent" , "Authorize");
        paymentDetails.put("canHandlePendingAuthorization", false);
        JSONObject chargeAmount = new JSONObject();
        chargeAmount.put("amount", amount.toString());
        chargeAmount.put("currencyCode", currency);
        paymentDetails.put("chargeAmount", chargeAmount);
        payload.put("paymentDetails", paymentDetails);

        return payload;
    }


}
