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
@RequestMapping("/v1/amazon")
@AllArgsConstructor
public class CheckoutController {

    @Autowired
    private AmazonPayService amazonPayService;

    @Autowired
    private JsonService jsonService;

    @GetMapping(path = "/create-checkout-session", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createCheckoutSession() throws AmazonPayClientException {
        System.out.println(jsonService.getPayLoadForCreateCheckout().toString());
        AmazonPayResponse response = amazonPayService.openCheckoutSession(jsonService.getPayLoadForCreateCheckout(),
                amazonPayService.generateIdempotencyKey());
        if(response.isSuccess()) {
            return new ResponseEntity<>(response.getRawResponse(), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(response.getRawResponse(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping(path = "/get-checkout-session/{sessionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getCheckoutSession(@PathVariable String sessionId) throws AmazonPayClientException {
        AmazonPayResponse response = amazonPayService.getCheckoutSession(sessionId);
        if(response.isSuccess()) {
            return new ResponseEntity<>(response.getRawResponse(), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(response.getRawResponse(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping(path = "/update-checkout-session/{sessionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateCheckoutSession(@PathVariable String sessionId, @RequestBody PaymentDto paymentDto) throws AmazonPayClientException {
        JSONObject jsonObject = jsonService.updatePayload(paymentDto.getAmount(), String.valueOf(paymentDto.getCurrency()));
        System.out.println(jsonObject.toString());
        AmazonPayResponse response = amazonPayService.updateCheckoutSession(sessionId, jsonObject);
        if(response.isSuccess()) {
            return new ResponseEntity<>(response.getRawResponse(), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(response.getRawResponse(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping(path = "/complete-checkout-session/{sessionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> completeCheckoutSession(@PathVariable String sessionId, @RequestBody PaymentDto paymentDto) throws AmazonPayClientException {
        JSONObject jsonObject = jsonService.getPayLoadForCompleteCheckout(paymentDto.getAmount(), String.valueOf(paymentDto.getCurrency()));
        System.out.println(jsonObject.toString());
        AmazonPayResponse response = amazonPayService.completeCheckoutSession(sessionId, jsonObject);
        if(response.isSuccess()) {
            return new ResponseEntity<>(response.getRawResponse(), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(response.getRawResponse(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
