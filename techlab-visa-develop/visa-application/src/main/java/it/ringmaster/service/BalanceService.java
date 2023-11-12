package it.ringmaster.service;

import com.stripe.Stripe;
import com.stripe.exception.*;
import com.stripe.model.Balance;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class BalanceService {

    @Value("${stripe.api.key}")
    String secretKey;

    public void init() {
        Stripe.apiKey = secretKey;
    }

    public Balance retrieveBalance() throws AuthenticationException,
            InvalidRequestException,  CardException {
        return null;
    }
}
