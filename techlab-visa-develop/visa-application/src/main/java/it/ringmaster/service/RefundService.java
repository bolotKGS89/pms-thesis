package it.ringmaster.service;

import com.stripe.exception.*;
import com.stripe.model.Refund;
import com.stripe.model.RefundCollection;
import it.ringmaster.dtos.RefundVisaDto;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class RefundService {


    public Refund create(RefundVisaDto refundVisaDto) throws
            StripeException {
        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("id", refundVisaDto.getId());
        chargeParams.put("amount", refundVisaDto.getAmount());
        chargeParams.put("currency", refundVisaDto.getCurrency());
        chargeParams.put("description", refundVisaDto.getDescription());
        chargeParams.put("payment_intent", refundVisaDto.getPaymentIntent());

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
