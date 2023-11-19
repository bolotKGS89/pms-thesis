package it.ringmaster.service;

import com.stripe.exception.StripeException;
import com.stripe.model.Balance;
import com.stripe.model.ChargeCollection;
import it.ringmaster.PaymentVisaDto;
import org.springframework.stereotype.Service;
import com.stripe.Stripe;
import com.stripe.exception.AuthenticationException;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;
import java.util.Map;

@Service
public class ChargeService {


    public Charge charge(PaymentVisaDto paymentVisaDto)
            throws StripeException {
        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("amount", paymentVisaDto.getAmount());
        chargeParams.put("currency", paymentVisaDto.getCurrency());
        chargeParams.put("description", paymentVisaDto.getDescription());
        chargeParams.put("source", paymentVisaDto.getStripeToken());

        return Charge.create(chargeParams);
    }

    public Charge retrieve(String id) throws StripeException
            {
        return Charge.retrieve(id);
    }

    public Charge update(String id, PaymentVisaDto paymentVisaDto) throws StripeException {
        Charge charge = Charge.retrieve(id);
        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("amount", paymentVisaDto.getAmount());
//        chargeParams.put("currency", paymentVisaDto.getCurrency());
        chargeParams.put("description", paymentVisaDto.getDescription());
        chargeParams.put("source", paymentVisaDto.getStripeToken());
        return charge.update(chargeParams);
    }

    public Charge capture(String id) throws StripeException {
        Charge charge =
                Charge.retrieve(id);
        return charge.capture();
    }

    public ChargeCollection getAll(Integer limit) throws StripeException
             {
        Map<String, Object> params = new HashMap<>();
        params.put("limit", limit);

        return Charge.list(params);
    }
}
