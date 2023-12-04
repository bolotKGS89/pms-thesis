package it.ringmaster.api;

import com.amazon.pay.api.AmazonPayResponse;
import com.amazon.pay.api.exceptions.AmazonPayClientException;
import it.ringmaster.dtos.MerchantMetadataDto;
import it.ringmaster.service.AmazonPayService;
import it.ringmaster.service.JsonService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/v1/amazon")
@AllArgsConstructor
public class ChargePermissionController {

    @Autowired
    private AmazonPayService amazonPayService;

    @Autowired
    private JsonService jsonService;

    @GetMapping(path = "/get-charge-permission/{permissionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getChargePermission(@PathVariable String permissionId) throws AmazonPayClientException {
        AmazonPayResponse response = amazonPayService.getChargePermission(permissionId);
        if(response.isSuccess()) {
            return new ResponseEntity<>(response.getRawResponse(), HttpStatus.FOUND);
        }
        return new ResponseEntity<>(response.getRawResponse(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping(path = "/update-charge-permission/{permissionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateChargePermission(@PathVariable String permissionId, @RequestBody MerchantMetadataDto merchantMetadataDto)
            throws AmazonPayClientException {
        JSONObject jsonObject = jsonService.updateChargePermission(merchantMetadataDto);
        AmazonPayResponse response = amazonPayService.updateChargePermission(permissionId, jsonObject);
        if(response.isSuccess()) {
            return new ResponseEntity<>(response.getRawResponse(), HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(response.getRawResponse(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping(path = "/close-charge-permission/{permissionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> closeChargePermission(@PathVariable String permissionId) throws AmazonPayClientException {
        //add payload later
        AmazonPayResponse response = amazonPayService.closeChargePermission(permissionId, null);
        if(response.isSuccess()) {
            return new ResponseEntity<>(response.getRawResponse(), HttpStatus.OK);
        }
        return new ResponseEntity<>(response.getRawResponse(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
