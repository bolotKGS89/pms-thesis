package it.ringmaster.service;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntentCollection;
import it.ringmaster.dtos.PaymentVisaDto;
import org.springframework.stereotype.Service;
import com.stripe.model.PaymentIntent;


import java.util.HashMap;
import java.util.Map;

@Service
public class PaymentIntentService {


    public PaymentIntent create(PaymentVisaDto paymentVisaDto)
            throws StripeException {
        Map<String, Object> automaticPaymentMethods =
                new HashMap<>();
        automaticPaymentMethods.put("enabled", true);
        automaticPaymentMethods.put("allow_redirects", "never");
        Map<String, Object> params = new HashMap<>();
        params.put("amount", paymentVisaDto.getAmount());
        params.put("currency", paymentVisaDto.getCurrency());
        params.put(
                "automatic_payment_methods",
                automaticPaymentMethods
        );

        return PaymentIntent.create(params);
    }

    public PaymentIntent retrieve(String id) throws StripeException
            {
        return PaymentIntent.retrieve(id);
    }

    public PaymentIntent confirm(String id) throws StripeException
    {
        PaymentIntent paymentIntent =
                PaymentIntent.retrieve(
                        id
                );

        Map<String, Object> params = new HashMap<>();
        params.put("payment_method", "pm_card_visa");

        return  paymentIntent.confirm(params);
    }

    public PaymentIntent update(String id, PaymentVisaDto paymentVisaDto) throws StripeException {
        PaymentIntent paymentIntent = PaymentIntent.retrieve(id);
        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("amount", paymentVisaDto.getAmount());
        return paymentIntent.update(chargeParams);
    }

    public PaymentIntent cancel(String id) throws StripeException {
        PaymentIntent paymentIntent =
                PaymentIntent.retrieve(id);
        return paymentIntent.cancel();
    }

    public PaymentIntentCollection getAll(Integer limit) throws StripeException
             {
        Map<String, Object> params = new HashMap<>();
        params.put("limit", limit);

        return PaymentIntent.list(params);
    }
}
