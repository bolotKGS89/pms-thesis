package it.ringmaster;

import lombok.Data;

import java.io.Serializable;

@Data
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

}
