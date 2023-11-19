package it.ringmaster;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Data
@Getter
@Setter
public class ResponseDto implements Serializable {
    private String id;
    private String balanceTransaction;
    private Long amount;
    private Long created;
    private String currency;
    private String description;
}
