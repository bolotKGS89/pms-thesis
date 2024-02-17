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

@RestController
@Slf4j
@RequestMapping("/visa")
@AllArgsConstructor
public class PaymentController {

    @Autowired
    private PaymentIntentService service;

    @PostMapping(path = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDto> create(@RequestHeader(value = "X-Request-ID", required = false) String xRequestId,
                                              @RequestHeader(value = "Service-Name", required = false) String serviceName,
                                              @RequestBody PaymentVisaDto paymentDto) {
        try {
            log.info("Receiving request (create) from {} (request_id {})", serviceName, xRequestId);
            PaymentIntent paymentIntent = service.create(paymentDto);
            ResponseDto responseDto = new ResponseDto();
            responseDto.setId(paymentIntent.getId());
            responseDto.setAmount(paymentIntent.getAmount());
            responseDto.setCreated(paymentIntent.getCreated());
            responseDto.setCurrency(paymentIntent.getCurrency());
            responseDto.setDescription(paymentIntent.getDescription());
            log.info("Responding to request (create) from {} (request_id {})", "techlab-visa-develop", xRequestId);
            return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
        } catch (StripeException e) {
            log.error("Error response (create) (code: {}) received from {} (request_id {})", e.getCause(), serviceName, xRequestId);
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/retrieve/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDto> retrieve(@RequestHeader(value = "X-Request-ID", required = false) String xRequestId,
                                                @RequestHeader(value = "Service-Name", required = false) String serviceName,
                                                @PathVariable String id) {
        try {
            log.info("Receiving request (retrieve) from {} (request_id {})", serviceName, xRequestId);
            PaymentIntent paymentIntent = service.retrieve(id);
            ResponseDto responseDto = new ResponseDto();
            responseDto.setId(paymentIntent.getId());
            responseDto.setAmount(paymentIntent.getAmount());
            responseDto.setCreated(paymentIntent.getCreated());
            responseDto.setCurrency(paymentIntent.getCurrency());
            responseDto.setDescription(paymentIntent.getDescription());
            log.info("Responding to request (retrieve) from {} (request_id {})", "techlab-visa-develop", xRequestId);
            return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
        } catch (StripeException e) {
            log.error("Error response (retrieve) (code: {}) received from {} (request_id {})", e.getCause(), serviceName, xRequestId);
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/confirm/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDto> confirm(@RequestHeader(value = "X-Request-ID", required = false) String xRequestId,
                                               @RequestHeader(value = "Service-Name", required = false) String serviceName,
                                               @PathVariable String id) {
        try {
            log.info("Receiving request (confirm) from {} (request_id {})", serviceName, xRequestId);
            PaymentIntent paymentIntent = service.confirm(id);
            ResponseDto responseDto = new ResponseDto();
            responseDto.setId(paymentIntent.getId());
            responseDto.setAmount(paymentIntent.getAmount());
            responseDto.setCreated(paymentIntent.getCreated());
            responseDto.setCurrency(paymentIntent.getCurrency());
            responseDto.setDescription(paymentIntent.getDescription());
            log.info("Responding to request (confirm) from {} (request_id {})", "techlab-visa-develop", xRequestId);
            return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
        } catch (StripeException e) {
            log.error("Error response (confirm) (code: {}) received from {} (request_id {})", e.getCause(), serviceName, xRequestId);
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(path = "/update/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDto> update(@RequestHeader(value = "X-Request-ID", required = false) String xRequestId,
                                              @RequestHeader(value = "Service-Name", required = false) String serviceName,
                                              @PathVariable String id,
                                              @RequestBody PaymentVisaDto paymentDto) {
        try {
            log.info("Receiving request (update) from {} (request_id {})", serviceName, xRequestId);
            PaymentIntent paymentIntent = service.update(id, paymentDto);
            ResponseDto responseDto = new ResponseDto();
            responseDto.setId(paymentIntent.getId());
            responseDto.setAmount(paymentIntent.getAmount());
            responseDto.setCreated(paymentIntent.getCreated());
            responseDto.setCurrency(paymentIntent.getCurrency());
            responseDto.setDescription(paymentIntent.getDescription());
            log.info("Responding to request (update) from {} (request_id {})", "techlab-visa-develop", xRequestId);
            return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
        } catch (StripeException e) {
            log.error("Error response (update) (code: {}) received from {} (request_id {})", e.getCause(), serviceName, xRequestId);
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/capture/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDto> capture(@RequestHeader(value = "X-Request-ID", required = false) String xRequestId,
                                               @RequestHeader(value = "Service-Name", required = false) String serviceName,
                                               @PathVariable String id) {
        try {
            log.info("Receiving request (capture) from {} (request_id {})", serviceName, xRequestId);
            PaymentIntent paymentIntent = service.cancel(id);
            ResponseDto responseDto = new ResponseDto();
            responseDto.setId(paymentIntent.getId());
            responseDto.setAmount(paymentIntent.getAmount());
            responseDto.setCreated(paymentIntent.getCreated());
            responseDto.setCurrency(paymentIntent.getCurrency());
            responseDto.setDescription(paymentIntent.getDescription());
            log.info("Responding to request (capture) from {} (request_id {})", "techlab-visa-develop", xRequestId);
            return new ResponseEntity<>(responseDto, HttpStatus.ACCEPTED);
        } catch (StripeException e) {
            log.error("Error response (capture) (code: {}) received from {} (request_id {})", e.getCause(), serviceName, xRequestId);
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/getAll/{limit}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getLimit(@RequestHeader(value = "X-Request-ID", required = false) String xRequestId,
                                           @RequestHeader(value = "Service-Name", required = false) String serviceName,
                                           @PathVariable Integer limit)  {
        try {
            log.info("Receiving request (getAll) from {} (request_id {})", serviceName, xRequestId);
            PaymentIntentCollection collection = service.getAll(limit);
            log.info("Responding to request (getAll) from {} (request_id {})", "techlab-visa-develop", xRequestId);
            return new ResponseEntity<>(collection.toJson(), HttpStatus.CREATED);
        } catch (StripeException e) {
            log.error("Error response (getAll) (code: {}) received from {} (request_id {})", e.getCause(), serviceName, xRequestId);
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path = "/refund/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDto> refund(@RequestHeader(value = "X-Request-ID", required = false) String xRequestId,
                                              @RequestHeader(value = "Service-Name", required = false) String serviceName,
                                              @PathVariable String id) {
        try {
            log.info("Receiving request (refund) from {} (request_id {})", serviceName, xRequestId);
            PaymentIntent paymentIntent = service.retrieve(id);
            paymentIntent.cancel();
            ResponseDto responseDto = new ResponseDto();
            responseDto.setId(paymentIntent.getId());
            responseDto.setAmount(paymentIntent.getAmount());
            responseDto.setCreated(paymentIntent.getCreated());
            responseDto.setCurrency(paymentIntent.getCurrency());
            responseDto.setDescription(paymentIntent.getDescription());
            log.info("Responding to request (refund) from {} (request_id {})", "techlab-visa-develop", xRequestId);
            return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
        } catch (StripeException e) {
            log.error("Error response (refund) (code: {}) received from {} (request_id {})", e.getCause(), serviceName, xRequestId);
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
