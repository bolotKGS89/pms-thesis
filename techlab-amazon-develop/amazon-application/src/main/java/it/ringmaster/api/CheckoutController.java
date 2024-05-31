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

import java.util.HashMap;
import java.util.Map;

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


        try {

//            Thread.sleep(5000);
            log.info("Received GET request from {} (request_id: {})", serviceName, xRequestId);

            if (serviceName.equals("amazon")) {
                log.error("amazon failed");
                throw new Exception();
            }
            AmazonPayResponse response = amazonPayService.openCheckoutSession(jsonService.getPayLoadForCreateCheckout(),
                    amazonPayService.generateIdempotencyKey());
            if(response.isSuccess()) {
                log.info("Answered to GET request from {} with code: {} (request_id: {})", serviceName, "200", xRequestId);
                return new ResponseEntity<>(response.getRawResponse(), HttpStatus.CREATED);
            }
            return new ResponseEntity<>(response.getRawResponse(), HttpStatus.INTERNAL_SERVER_ERROR);
        }  catch (Exception e) {
            log.error("Answered to GET request from {} with code: {} (request_id: {})", serviceName, "500", xRequestId);
            e.printStackTrace();
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("serviceName", "amazon");
            errorResponse.put("message", e.getMessage());
            return new ResponseEntity<>("error", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping(path = "/get-checkout-session/{sessionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getCheckoutSession(@RequestHeader(value = "X-Request-ID", required = false) String xRequestId,
                                                     @RequestHeader(value = "Service-Name", required = false) String serviceName,
                                                     @PathVariable String sessionId) throws AmazonPayClientException {
        log.info("Received GET request from {} (request_id {})", "127.0.0.1", xRequestId);
        AmazonPayResponse response = amazonPayService.getCheckoutSession(sessionId);
        if(response.isSuccess()) {
            log.info("Answered to GET request from {} with code: {} (request_id {})", "127.0.0.1", "200", xRequestId);
            return new ResponseEntity<>(response.getRawResponse(), HttpStatus.CREATED);
        }
        log.error("Answered to GET request from {} with code: {} (request_id {})", "127.0.0.1", "500", xRequestId);
        return new ResponseEntity<>(response.getRawResponse(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping(path = "/update-checkout-session/{sessionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateCheckoutSession(@RequestHeader(value = "X-Request-ID", required = false) String xRequestId,
                                                        @RequestHeader(value = "Service-Name", required = false) String serviceName,
                                                        @PathVariable String sessionId, @RequestBody PaymentDto paymentDto) throws AmazonPayClientException {
        log.info("Received PUT request from {} (request_id {})", "127.0.0.1", xRequestId);
        JSONObject jsonObject = jsonService.updatePayload(paymentDto.getAmount(), String.valueOf(paymentDto.getCurrency()));
        AmazonPayResponse response = amazonPayService.updateCheckoutSession(sessionId, jsonObject);
        if(response.isSuccess()) {
            log.info("Answered to PUT request from {} with code: {} (request_id {})", "127.0.0.1", "200", xRequestId);
            return new ResponseEntity<>(response.getRawResponse(), HttpStatus.CREATED);
        }
        log.error("Answered to PUT request from {} with code: {} (request_id {})", "127.0.0.1", "500", xRequestId);
        return new ResponseEntity<>(response.getRawResponse(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping(path = "/complete-checkout-session/{sessionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> completeCheckoutSession(@RequestHeader(value = "X-Request-ID", required = false) String xRequestId,
                                                          @RequestHeader(value = "Service-Name", required = false) String serviceName,
                                                          @PathVariable String sessionId, @RequestBody PaymentDto paymentDto) throws AmazonPayClientException {
        log.info("Received POST request from {} (request_id {})", "127.0.0.1", xRequestId);
        JSONObject jsonObject = jsonService.getPayLoadForCompleteCheckout(paymentDto.getAmount(), String.valueOf(paymentDto.getCurrency()));
        AmazonPayResponse response = amazonPayService.completeCheckoutSession(sessionId, jsonObject);
        if(response.isSuccess()) {
            log.info("Answered to POST request from {} with code: {} (request_id {})", "127.0.0.1", "200", xRequestId);
            return new ResponseEntity<>(response.getRawResponse(), HttpStatus.CREATED);
        }
        log.error("Answered to POST request from {} with code: {} (request_id {})", "127.0.0.1", "500", xRequestId);
        return new ResponseEntity<>(response.getRawResponse(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
