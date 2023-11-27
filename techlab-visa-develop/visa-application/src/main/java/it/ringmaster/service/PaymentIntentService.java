package it.ringmaster.service;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntentCollection;
import it.ringmaster.PaymentVisaDto;
import org.springframework.stereotype.Service;
import com.stripe.model.PaymentIntent;


import java.util.HashMap;
import java.util.Map;

@Service
public class PaymentIntentService {


    public PaymentIntent create(PaymentVisaDto paymentVisaDto)
            throws StripeException {
        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("amount", paymentVisaDto.getAmount());
        chargeParams.put("currency", paymentVisaDto.getCurrency());
        chargeParams.put("description", paymentVisaDto.getDescription());
        chargeParams.put("source", paymentVisaDto.getStripeToken());

        return PaymentIntent.create(chargeParams);
    }

    public PaymentIntent retrieve(String id) throws StripeException
            {
        return PaymentIntent.retrieve(id);
    }

    public PaymentIntent update(String id, PaymentVisaDto paymentVisaDto) throws StripeException {
        PaymentIntent paymentIntent = PaymentIntent.retrieve(id);
        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("amount", paymentVisaDto.getAmount());
        chargeParams.put("description", paymentVisaDto.getDescription());
        chargeParams.put("source", paymentVisaDto.getStripeToken());
        return paymentIntent.update(chargeParams);
    }

    public PaymentIntent capture(String id) throws StripeException {
        PaymentIntent paymentIntent =
                PaymentIntent.retrieve(id);
        return paymentIntent.capture();
    }

    public PaymentIntentCollection getAll(Integer limit) throws StripeException
             {
        Map<String, Object> params = new HashMap<>();
        params.put("limit", limit);

        return PaymentIntent.list(params);
    }
}
