package it.ringmaster.api;

import com.amazon.pay.api.AmazonPayResponse;
import com.amazon.pay.api.exceptions.AmazonPayClientException;
import it.ringmaster.dtos.PaymentDto;
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
@RequestMapping("/v1/charge")
@AllArgsConstructor
public class ChargeController {

    @Autowired
    private AmazonPayService amazonPayService;

    @Autowired
    private JsonService jsonService;

    @GetMapping(path = "/get-charge/{chargeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getCharge(@PathVariable String chargeId) throws AmazonPayClientException {
        AmazonPayResponse response = amazonPayService.getCharge(chargeId);
        if(response.isSuccess()) {
            return new ResponseEntity<>(response.getRawResponse(), HttpStatus.FOUND);
        }
        return new ResponseEntity<>(response.getRawResponse(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping(path = "/create-charge/{permissionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getCharge(@PathVariable String permissionId, @RequestBody PaymentDto paymentDto) throws AmazonPayClientException {
        JSONObject jsonObject = jsonService.createCharge(paymentDto, permissionId, false);
        AmazonPayResponse response = amazonPayService.createCharge(jsonObject, amazonPayService.generateIdempotencyKey());
        if(response.isSuccess()) {
            return new ResponseEntity<>(response.getRawResponse(), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(response.getRawResponse(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping(path = "/create-charge/{captureId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> captureCharge(@PathVariable String captureId, @RequestBody PaymentDto paymentDto) throws AmazonPayClientException {
        JSONObject jsonObject = jsonService.createCharge(paymentDto, null, false);
        AmazonPayResponse response = amazonPayService.captureCharge(captureId, jsonObject, amazonPayService.generateIdempotencyKey());
        if(response.isSuccess()) {
            return new ResponseEntity<>(response.getRawResponse(), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(response.getRawResponse(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping(path = "/cancel-charge/{chargeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> cancelCharge(@PathVariable String chargeId) throws AmazonPayClientException {
        JSONObject payload = new JSONObject();
        payload.put("cancellationReason", "Buyer changed their mind");
        AmazonPayResponse response = amazonPayService.cancelCharge(chargeId, payload);
        if(response.isSuccess()) {
            return new ResponseEntity<>(response.getRawResponse(), HttpStatus.FOUND);
        }
        return new ResponseEntity<>(response.getRawResponse(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
