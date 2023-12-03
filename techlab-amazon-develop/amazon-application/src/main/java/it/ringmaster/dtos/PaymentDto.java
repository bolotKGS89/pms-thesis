package it.ringmaster.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Data
public class PaymentDto implements Serializable {
    private Double amount;
    private String  currency;
}
