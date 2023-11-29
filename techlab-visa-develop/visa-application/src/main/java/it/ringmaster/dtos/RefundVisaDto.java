package it.ringmaster.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Data
@Getter
@Setter
public class RefundVisaDto implements Serializable {
    public enum Currency {
        EUR, USD;
    }

    private String id;
    private Integer amount;
    private String charge;
    private Currency currency;
    private String description;
    private String status;
    private String paymentIntent;

}

