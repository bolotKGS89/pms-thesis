package it.ringmaster.dtos;

import lombok.Data;

import java.io.Serializable;

@Data
public class MerchantMetadataDto implements Serializable {
    private String merchantReferenceId;
    private String merchantStoreName;
    private String noteToBuyer;
    private String customInformation;
}
