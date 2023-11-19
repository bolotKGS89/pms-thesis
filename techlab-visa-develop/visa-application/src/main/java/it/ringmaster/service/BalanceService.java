package it.ringmaster.service;

import com.stripe.Stripe;
import com.stripe.exception.*;
import com.stripe.model.Balance;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class BalanceService {

    public Balance retrieveBalance() throws StripeException {
        return Balance.retrieve();
    }
}
