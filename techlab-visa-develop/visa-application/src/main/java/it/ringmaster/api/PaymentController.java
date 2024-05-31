package it.ringmaster.api;


import com.stripe.exception.*;
import com.stripe.model.PaymentIntent;
import com.stripe.model.PaymentIntentCollection;
import it.ringmaster.dtos.PaymentVisaDto;
import it.ringmaster.dtos.ResponseDto;
import it.ringmaster.service.PaymentIntentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import java.net.InetAddress;
import java.net.UnknownHostException;

@RestController
@Slf4j
@RequestMapping("/visa")
@AllArgsConstructor
public class PaymentController {

    @Autowired
    private PaymentIntentService service;

    @PostMapping(path = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> create(@RequestHeader(value = "X-Request-ID", required = false) String xRequestId,
                                              @RequestHeader(value = "Service-Name", required = false) String serviceName,
                                              @RequestBody PaymentVisaDto paymentDto) {
        try {
            log.info("Received POST request from {} (request_id: {})", serviceName, xRequestId);
            if (serviceName.equals("visa")) {
                log.error("visa failed");
                throw new Exception();
            }
            PaymentIntent paymentIntent = service.create(paymentDto);
            ResponseDto responseDto = new ResponseDto();
            responseDto.setId(paymentIntent.getId());
            responseDto.setAmount(paymentIntent.getAmount());
            responseDto.setCreated(paymentIntent.getCreated());
            responseDto.setCurrency(paymentIntent.getCurrency());
            responseDto.setDescription(paymentIntent.getDescription());
            log.info("Answered to POST request from {} with code: {} (request_id: {})", serviceName, "200", xRequestId);
            return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Answered to POST request from {} with code: {} (request_id: {})", serviceName, "500", xRequestId);
            e.printStackTrace();
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("serviceName", "visa");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping(path = "/retrieve/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> retrieve(@RequestHeader(value = "X-Request-ID", required = false) String xRequestId,
                                                @RequestHeader(value = "Service-Name", required = false) String serviceName,
                                                @PathVariable String id) throws UnknownHostException {
        try {
            log.info("Received GET request from {} (request_id {})", InetAddress.getLocalHost().getHostAddress(), xRequestId);
            PaymentIntent paymentIntent = service.retrieve(id);
            ResponseDto responseDto = new ResponseDto();
            responseDto.setId(paymentIntent.getId());
            responseDto.setAmount(paymentIntent.getAmount());
            responseDto.setCreated(paymentIntent.getCreated());
            responseDto.setCurrency(paymentIntent.getCurrency());
            responseDto.setDescription(paymentIntent.getDescription());
            log.info("Answered to GET request from {} with code: {} (request_id {})", InetAddress.getLocalHost().getHostAddress(), "200", xRequestId);
            return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
        } catch (StripeException e) {
            log.error("Answered to GET request from {} with code: {} (request_id {})", InetAddress.getLocalHost().getHostAddress(), "500", xRequestId);
            e.printStackTrace();
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("serviceName", "techlab_visa");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping(path = "/confirm/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> confirm(@RequestHeader(value = "X-Request-ID", required = false) String xRequestId,
                                               @RequestHeader(value = "Service-Name", required = false) String serviceName,
                                               @PathVariable String id) throws UnknownHostException {
        try {
            log.info("Received GET request from {} (request_id {})", InetAddress.getLocalHost().getHostAddress(), xRequestId);
            PaymentIntent paymentIntent = service.confirm(id);
            ResponseDto responseDto = new ResponseDto();
            responseDto.setId(paymentIntent.getId());
            responseDto.setAmount(paymentIntent.getAmount());
            responseDto.setCreated(paymentIntent.getCreated());
            responseDto.setCurrency(paymentIntent.getCurrency());
            responseDto.setDescription(paymentIntent.getDescription());
            log.info("Answered to GET request from {} with code: {} (request_id {})", InetAddress.getLocalHost().getHostAddress(), "200", xRequestId);
            return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
        } catch (StripeException | UnknownHostException e) {
            log.error("Answered to GET request from {} with code: {} (request_id {})", InetAddress.getLocalHost().getHostAddress(), "500", xRequestId);
            e.printStackTrace();
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("serviceName", "techlab_visa");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PutMapping(path = "/update/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> update(@RequestHeader(value = "X-Request-ID", required = false) String xRequestId,
                                              @RequestHeader(value = "Service-Name", required = false) String serviceName,
                                              @PathVariable String id,
                                              @RequestBody PaymentVisaDto paymentDto) throws UnknownHostException {
        try {
            log.info("Received PUT request from {} (request_id {})", InetAddress.getLocalHost().getHostAddress(), xRequestId);
            PaymentIntent paymentIntent = service.update(id, paymentDto);
            ResponseDto responseDto = new ResponseDto();
            responseDto.setId(paymentIntent.getId());
            responseDto.setAmount(paymentIntent.getAmount());
            responseDto.setCreated(paymentIntent.getCreated());
            responseDto.setCurrency(paymentIntent.getCurrency());
            responseDto.setDescription(paymentIntent.getDescription());
            log.info("Answered to PUT request from {} with code: {} (request_id {})", InetAddress.getLocalHost().getHostAddress(), "200", xRequestId);
            return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
        } catch (StripeException | UnknownHostException e) {
            log.error("Answered to PUT request from {} with code: {} (request_id {})", InetAddress.getLocalHost().getHostAddress(), "500", xRequestId);
            e.printStackTrace();
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("serviceName", "techlab_visa");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping(path = "/capture/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> capture(@RequestHeader(value = "X-Request-ID", required = false) String xRequestId,
                                               @RequestHeader(value = "Service-Name", required = false) String serviceName,
                                               @PathVariable String id)  throws UnknownHostException {
        try {
            log.info("Received GET request from {} (request_id {})", InetAddress.getLocalHost().getHostAddress(), xRequestId);
            PaymentIntent paymentIntent = service.cancel(id);
            ResponseDto responseDto = new ResponseDto();
            responseDto.setId(paymentIntent.getId());
            responseDto.setAmount(paymentIntent.getAmount());
            responseDto.setCreated(paymentIntent.getCreated());
            responseDto.setCurrency(paymentIntent.getCurrency());
            responseDto.setDescription(paymentIntent.getDescription());
            log.info("Answered to GET request from {} with code: {} (request_id {})", InetAddress.getLocalHost().getHostAddress(), "200", xRequestId);
            return new ResponseEntity<>(responseDto, HttpStatus.ACCEPTED);
        } catch (StripeException e) {
            log.error("Answered to GET request from {} with code: {} (request_id {})", InetAddress.getLocalHost().getHostAddress(), "500", xRequestId);
            e.printStackTrace();
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("serviceName", "techlab_visa");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping(path = "/getAll/{limit}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getLimit(@RequestHeader(value = "X-Request-ID", required = false) String xRequestId,
                                           @RequestHeader(value = "Service-Name", required = false) String serviceName,
                                           @PathVariable Integer limit) throws UnknownHostException  {
        try {
            log.info("Received GET request from {} (request_id {})", InetAddress.getLocalHost().getHostAddress(), xRequestId);
            PaymentIntentCollection collection = service.getAll(limit);
            log.info("Answered to GET request from {} with code: {} (request_id {})", InetAddress.getLocalHost().getHostAddress(), "200", xRequestId);
            return new ResponseEntity<>(collection.toJson(), HttpStatus.CREATED);
        } catch (StripeException e) {
            log.error("Answered to GET request from {} with code: {} (request_id {})", InetAddress.getLocalHost().getHostAddress(), "500", xRequestId);
            e.printStackTrace();
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("serviceName", "techlab_visa");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PostMapping(path = "/refund/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> refund(@RequestHeader(value = "X-Request-ID", required = false) String xRequestId,
                                              @RequestHeader(value = "Service-Name", required = false) String serviceName,
                                              @PathVariable String id) throws UnknownHostException {
        try {
            log.info("Received POST request from {} (request_id {})", "127.0.0.1", xRequestId);
            PaymentIntent paymentIntent = service.retrieve(id);
            paymentIntent.cancel();
            ResponseDto responseDto = new ResponseDto();
            responseDto.setId(paymentIntent.getId());
            responseDto.setAmount(paymentIntent.getAmount());
            responseDto.setCreated(paymentIntent.getCreated());
            responseDto.setCurrency(paymentIntent.getCurrency());
            responseDto.setDescription(paymentIntent.getDescription());
            log.info("Answered to POST request from {} with code: {} (request_id {})", "127.0.0.1", "200", xRequestId);
            return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
        } catch (StripeException e) {
            log.error("Answered to POST request from {} with code: {} (request_id {})", "127.0.0.1", "500", xRequestId);
            e.printStackTrace();
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("serviceName", "techlab_visa");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
