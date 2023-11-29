package it.ringmaster;

import lombok.Data;

import java.io.Serializable;

@Data
public class PaymentVisaDto implements Serializable {
    private Integer amount;
    private String currency;
    private String description;
}
