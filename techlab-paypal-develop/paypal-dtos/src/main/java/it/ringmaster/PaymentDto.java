package it.ringmaster;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDto implements Serializable {
    private String status;
    private String payId;
    private String redirectUrl;

    public PaymentDto(String status) {
        this.status = status;
    }
}
