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
@RequestMapping("/amazon")
@AllArgsConstructor
public class RefundController {
    @Autowired
    private AmazonPayService amazonPayService;

    @Autowired
    private JsonService jsonService;

    @PostMapping(path = "/create-refund/{chargeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createRefund(@PathVariable String chargeId, @RequestBody PaymentDto paymentDto) throws AmazonPayClientException {
        JSONObject jsonObject = jsonService.createRefund(paymentDto, chargeId);
        AmazonPayResponse response = amazonPayService.createRefund(jsonObject, amazonPayService.generateIdempotencyKey());
        if(response.isSuccess()) {
            return new ResponseEntity<>(response.getRawResponse(), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(response.getRawResponse(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping(path = "/get-refund/{refundId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createRefund(@PathVariable String refundId) throws AmazonPayClientException  {
        AmazonPayResponse response = amazonPayService.getRefund(refundId);
        if(response.isSuccess()) {
            return new ResponseEntity<>(response.getRawResponse(), HttpStatus.OK);
        }
        return new ResponseEntity<>(response.getRawResponse(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
