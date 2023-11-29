package it.ringmaster.dtos;

import lombok.*;

import java.io.Serializable;

@Data
public class PaymentDto implements Serializable {
    private String intent;
    private Double total;
    private String currency;
    private String payerId;
    private String note;
}
