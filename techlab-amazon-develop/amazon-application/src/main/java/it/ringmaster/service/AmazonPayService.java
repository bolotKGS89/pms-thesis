package it.ringmaster.service;

import com.amazon.pay.api.AmazonPayResponse;
import com.amazon.pay.api.PayConfiguration;
import com.amazon.pay.api.WebstoreClient;
import com.amazon.pay.api.exceptions.AmazonPayClientException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class AmazonPayService {
    private final WebstoreClient webstoreClient;

    @Autowired
    public AmazonPayService(PayConfiguration payConfiguration) throws AmazonPayClientException {
        this.webstoreClient = new WebstoreClient(payConfiguration);
    }

    public AmazonPayResponse openCheckoutSession(JSONObject payload, Map<String, String> header) throws AmazonPayClientException {
        return this.webstoreClient.createCheckoutSession(payload, header);
    }

    public Map<String,String> generateIdempotencyKey() {
        Map<String,String> header = new HashMap<String,String>();
        header.put("x-amz-pay-idempotency-key", UUID.randomUUID().toString().replace("-", ""));
        return header;
    }

    public AmazonPayResponse getCheckoutSession(String sessionId) throws AmazonPayClientException {
        return this.webstoreClient.getCheckoutSession(sessionId);
    }

    public AmazonPayResponse updateCheckoutSession(String sessionId, JSONObject payload) throws AmazonPayClientException {
        return this.webstoreClient.updateCheckoutSession(sessionId, payload);
    }

    public AmazonPayResponse completeCheckoutSession(String sessionId, JSONObject payload) throws AmazonPayClientException {
        return this.webstoreClient.completeCheckoutSession(sessionId, payload);
    }

    public AmazonPayResponse getChargePermission(String sessionId) throws AmazonPayClientException {
        return this.webstoreClient.getChargePermission(sessionId);
    }

    public AmazonPayResponse updateChargePermission(String sessionId, JSONObject payload) throws AmazonPayClientException {
        return this.webstoreClient.updateChargePermission(sessionId, payload);
    }

    public AmazonPayResponse closeChargePermission(String sessionId, JSONObject payload) throws AmazonPayClientException {
        return this.webstoreClient.closeChargePermission(sessionId, payload);
    }

    public AmazonPayResponse getCharge(String chargeId) throws AmazonPayClientException {
        return this.webstoreClient.getCharge(chargeId);
    }

    public AmazonPayResponse createCharge(JSONObject payload, Map<String, String> header) throws AmazonPayClientException {
        return this.webstoreClient.createCharge(payload, header);
    }

    public AmazonPayResponse captureCharge(String chargeId, JSONObject payload, Map<String, String> header) throws AmazonPayClientException {
        return this.webstoreClient.captureCharge(chargeId, payload, header);
    }

    public AmazonPayResponse cancelCharge(String chargeId, JSONObject payload) throws AmazonPayClientException {
        return this.webstoreClient.cancelCharge(chargeId, payload);
    }

    public AmazonPayResponse createRefund(JSONObject payload, Map<String, String> header) throws AmazonPayClientException {
        return this.webstoreClient.createRefund(payload, header);
    }

    public AmazonPayResponse getRefund(String refundId)  throws AmazonPayClientException {
        return this.webstoreClient.getRefund(refundId);
    }
}
