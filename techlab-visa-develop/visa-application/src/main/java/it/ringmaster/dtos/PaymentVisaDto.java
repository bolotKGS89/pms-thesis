package it.ringmaster.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Data
public class PaymentVisaDto implements Serializable {
    private Integer amount;
    private String currency;
    private String description;
}
