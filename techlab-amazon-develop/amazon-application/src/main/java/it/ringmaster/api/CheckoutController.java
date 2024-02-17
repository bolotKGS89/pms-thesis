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
public class CheckoutController {

    @Autowired
    private AmazonPayService amazonPayService;

    @Autowired
    private JsonService jsonService;

    @GetMapping(path = "/create-checkout-session", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createCheckoutSession(@RequestHeader(value = "X-Request-ID", required = false) String xRequestId,
                                                        @RequestHeader(value = "Service-Name", required = false) String serviceName) throws AmazonPayClientException {
        log.info("Receiving request (create-checkout-session) from {} (request_id {})", serviceName, xRequestId);
        AmazonPayResponse response = amazonPayService.openCheckoutSession(jsonService.getPayLoadForCreateCheckout(),
                amazonPayService.generateIdempotencyKey());
        if(response.isSuccess()) {
            log.info("Responding to request (create-checkout-session) from {} (request_id {})", "techlab-amazon-develop", xRequestId);
            return new ResponseEntity<>(response.getRawResponse(), HttpStatus.CREATED);
        }
        log.error("Error response (create-checkout-session) (code: {}) received from {} (request_id {})", response.getStatus(), "techlab-amazon-develop", xRequestId);
        return new ResponseEntity<>(response.getRawResponse(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping(path = "/get-checkout-session/{sessionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getCheckoutSession(@RequestHeader(value = "X-Request-ID", required = false) String xRequestId,
                                                     @RequestHeader(value = "Service-Name", required = false) String serviceName,
                                                     @PathVariable String sessionId) throws AmazonPayClientException {
        log.info("Receiving request (get-checkout-session) from {} (request_id {})", serviceName, xRequestId);
        AmazonPayResponse response = amazonPayService.getCheckoutSession(sessionId);
        if(response.isSuccess()) {
            log.info("Responding to request (get-checkout-session) from {} (request_id {})", "techlab-amazon-develop", xRequestId);
            return new ResponseEntity<>(response.getRawResponse(), HttpStatus.CREATED);
        }
        log.error("Error response (get-checkout-session) (code: {}) received from {} (request_id {})", response.getStatus(), "techlab-amazon-develop", xRequestId);
        return new ResponseEntity<>(response.getRawResponse(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping(path = "/update-checkout-session/{sessionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateCheckoutSession(@RequestHeader(value = "X-Request-ID", required = false) String xRequestId,
                                                        @RequestHeader(value = "Service-Name", required = false) String serviceName,
                                                        @PathVariable String sessionId, @RequestBody PaymentDto paymentDto) throws AmazonPayClientException {
        log.info("Receiving request (update-checkout-session) from {} (request_id {})", serviceName, xRequestId);
        JSONObject jsonObject = jsonService.updatePayload(paymentDto.getAmount(), String.valueOf(paymentDto.getCurrency()));
        AmazonPayResponse response = amazonPayService.updateCheckoutSession(sessionId, jsonObject);
        if(response.isSuccess()) {
            log.info("Responding to request (update-checkout-session) from {} (request_id {})", "techlab-amazon-develop", xRequestId);
            return new ResponseEntity<>(response.getRawResponse(), HttpStatus.CREATED);
        }
        log.error("Error response (update-checkout-session) (code: {}) received from {} (request_id {})", response.getStatus(), "techlab-amazon-develop", xRequestId);
        return new ResponseEntity<>(response.getRawResponse(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping(path = "/complete-checkout-session/{sessionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> completeCheckoutSession(@RequestHeader(value = "X-Request-ID", required = false) String xRequestId,
                                                          @RequestHeader(value = "Service-Name", required = false) String serviceName,
                                                          @PathVariable String sessionId, @RequestBody PaymentDto paymentDto) throws AmazonPayClientException {
        log.info("Receiving request (complete-checkout-session) from {} (request_id {})", serviceName, xRequestId);
        JSONObject jsonObject = jsonService.getPayLoadForCompleteCheckout(paymentDto.getAmount(), String.valueOf(paymentDto.getCurrency()));
        AmazonPayResponse response = amazonPayService.completeCheckoutSession(sessionId, jsonObject);
        if(response.isSuccess()) {
            log.info("Responding to request (complete-checkout-session) from {} (request_id {})", "techlab-amazon-develop", xRequestId);
            return new ResponseEntity<>(response.getRawResponse(), HttpStatus.CREATED);
        }
        log.error("Error response (complete-checkout-session) (code: {}) received from {} (request_id {})", response.getStatus(), "techlab-amazon-develop", xRequestId);
        return new ResponseEntity<>(response.getRawResponse(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
