package it.ringmaster.service;

import com.stripe.Stripe;
import com.stripe.exception.*;
import com.stripe.model.Refund;
import com.stripe.model.RefundCollection;
import it.ringmaster.PaymentVisaDto;
import it.ringmaster.RefundVisaDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class RefundService {

    @Value("${stripe.api.key}")
    String secretKey;

    public void init() {
        Stripe.apiKey = secretKey;
    }

    public Refund create(RefundVisaDto refundVisaDto) throws
            StripeException {
        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("id", refundVisaDto.getId());
        chargeParams.put("amount", refundVisaDto.getAmount());
        chargeParams.put("charge", refundVisaDto.getCharge());
        chargeParams.put("currency", refundVisaDto.getCurrency());

        return Refund.create(chargeParams);
    }

    public Refund retrieve(String id) throws
             StripeException {
        return Refund.retrieve(id);
    }

    public Refund update(String id, RefundVisaDto refundVisaDto) throws StripeException {
        Refund refund = Refund.retrieve(id);
        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("id", refundVisaDto.getId());
        chargeParams.put("amount", refundVisaDto.getAmount());
        chargeParams.put("charge", refundVisaDto.getCharge());
        chargeParams.put("currency", refundVisaDto.getCurrency());
        return refund.update(chargeParams);
    }

    public RefundCollection getAll(Integer limit) throws StripeException
            {
        Map<String, Object> params = new HashMap<>();
        params.put("limit", limit);

        return Refund.list(params);
    }
}
