package it.ringmaster.service;

import it.ringmaster.dtos.MerchantMetadataDto;
import it.ringmaster.dtos.PaymentDto;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.Currency;
import java.util.Optional;

@Service
public class JsonService {
    private static final String CHECKOUT_URL = "https://www.reply.com/it";
    private static final String STORE_ID = "amzn1.application-oa2-client.746614401d0941c5abf825ab9bdf8c6f";

    public JSONObject getPayLoadForCreateCheckout() {
        JSONObject payload = new JSONObject();
        JSONObject webCheckoutDetails = new JSONObject();
        webCheckoutDetails.put("checkoutReviewReturnUrl", CHECKOUT_URL);
        webCheckoutDetails.put("checkoutResultReturnUrl", CHECKOUT_URL);
//        webCheckoutDetails.put("checkoutMode", "ProcessOrder");

        JSONObject paymentDetails = new JSONObject();
        paymentDetails.put("paymentIntent", "Confirm");
//        paymentDetails.put("chargeAmount", new JSONObject()
//                .put("amount", "1.23")
//                .put("currencyCode", "USD"));

//        JSONObject recurringMetadata = new JSONObject();


        payload.put("webCheckoutDetails", webCheckoutDetails);
        payload.put("storeId", STORE_ID);
        payload.put("paymentDetails", paymentDetails);
        payload.put("chargePermissionType", "OneTime");
//        payload.put("productType", "PayAndShip");

//        payload.put("recurringMetadata", recurringMetadata);
//        recurringMetadata.put("frequency", new JSONObject()
//                .put("unit", "Variable")
//                .put("value", "0"));

        payload.put("scopes", new JSONArray().put("name")
                        .put("email")
                        .put("phoneNumber")
                .put("shippingAddress"));
//
//        JSONObject addressDetails = new JSONObject();
//        addressDetails
//                .put("name", "Giuseppe Rossi")
//                .put("addressLine2", "Via Francesco Saverio Nitti; 30")
//                .put("city", "Rome")
//                .put("stateOrRegion", "Lazio")
//                .put("postalCode", "00156")
//                .put("countryCode", "IT")
//                .put("phoneNumber", "+880 9900-111111");
//
//        payload.put("shippingAddress", addressDetails);



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

        JSONObject merchantMetadata = new JSONObject();
        merchantMetadata.put("merchantReferenceId", "2019-0001");
        merchantMetadata.put("merchantStoreName", "AmazonTestStoreFront");
        merchantMetadata.put("noteToBuyer", "noteToBuyer");
        merchantMetadata.put("customInformation", "custom information goes here");
        payload.put("merchantMetadata", merchantMetadata);

        return payload;
    }

    public JSONObject updateChargePermission(MerchantMetadataDto merchantMetadataDto) {
        JSONObject payload = new JSONObject();
        JSONObject merchantMetadata = new JSONObject();
        merchantMetadata.put("merchantReferenceId", merchantMetadataDto.getMerchantReferenceId());
        merchantMetadata.put("merchantStoreName", merchantMetadataDto.getMerchantStoreName());
        merchantMetadata.put("noteToBuyer", merchantMetadataDto.getNoteToBuyer());
        merchantMetadata.put("customInformation", merchantMetadataDto.getCustomInformation());
        payload.put("merchantMetadata", merchantMetadata);

        return payload;
    }

    public JSONObject createCharge(PaymentDto paymentDto, String permissionId, Boolean captureNow) {
        JSONObject payload = new JSONObject();
        JSONObject chargeAmount = new JSONObject();
        chargeAmount.put("amount", paymentDto.getAmount());
        chargeAmount.put("currencyCode", paymentDto.getCurrency());

        if(!Optional.of(permissionId).isEmpty())
            payload.put("chargePermissionId", permissionId);

        payload.put("chargeAmount", chargeAmount);
        payload.put("captureNow", captureNow);
        // if payload.put("captureNow", true);
        // then provide
        // payload.put("softDescriptor", "My Soft Descriptor");
        payload.put("canHandlePendingAuthorization", true);
        return payload;
    }

    public JSONObject createRefund(PaymentDto paymentDto, String chargeId) {
        JSONObject payload = new JSONObject();
        JSONObject refundAmount = new JSONObject();
        refundAmount.put("amount", paymentDto.getAmount());
        refundAmount.put("currencyCode", paymentDto.getCurrency());
        payload.put("chargeId", chargeId);
        payload.put("refundAmount", refundAmount);
        payload.put("softDescriptor", "AMZ*soft");

        return payload;
    }

}
