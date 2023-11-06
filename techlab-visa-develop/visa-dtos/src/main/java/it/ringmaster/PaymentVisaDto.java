package it.ringmaster;

import lombok.Data;

import java.io.Serializable;

@Data
public class PaymentVisaDto implements Serializable {
    public enum Currency {
        EUR, USD;
    }
    private String description;
    private int amount;
    private PaymentVisaDto.Currency currency;
    private String stripeEmail;
    private String stripeToken;
}
