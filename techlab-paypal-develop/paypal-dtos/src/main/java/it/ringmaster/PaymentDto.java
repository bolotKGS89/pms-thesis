package it.ringmaster;

import lombok.*;

import java.io.Serializable;

@Data
public class PaymentDto implements Serializable {
    private String intent;
    private Double total;
    private String currency;
}
