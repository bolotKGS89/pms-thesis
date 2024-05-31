package it.ringmaster.api;

import com.stripe.exception.StripeException;
import com.stripe.model.Refund;
import com.stripe.model.RefundCollection;
import it.ringmaster.dtos.RefundVisaDto;
import it.ringmaster.dtos.ResponseDto;
import it.ringmaster.service.RefundService;
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
public class RefundController {

    @Autowired
    private RefundService service;

    @PostMapping(path = "/refund/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDto> create(@RequestHeader(value = "X-Request-ID", required = false) String xRequestId,
                                              @RequestHeader(value = "Service-Name", required = false) String serviceName,
                                              @RequestBody RefundVisaDto refundVisaDto) throws StripeException {
        try {
            log.info("Received POST request from {} (request_id {})", "127.0.0.1", xRequestId);
            Refund refund = service.create(refundVisaDto);
            ResponseDto responseDto = new ResponseDto();
            responseDto.setId(refund.getId());
            responseDto.setBalanceTransaction(refund.getBalanceTransaction());
            responseDto.setAmount(refund.getAmount());
            responseDto.setCreated(refund.getCreated());
            responseDto.setCurrency(refund.getCurrency());
            responseDto.setDescription(refund.getDescription());
            log.info("Answered to POST request from {} with code: {} (request_id {})", "127.0.0.1", "200", xRequestId);
            return new ResponseEntity<>(responseDto, HttpStatus.ACCEPTED);
        } catch (StripeException e) {
            log.error("Answered to POST request from {} with code: {} (request_id {})", "127.0.0.1", "500", xRequestId);
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/refund/retrieve/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public RefundVisaDto retrieve(@PathVariable String id) throws StripeException {
        service.retrieve(id);
        return null;
    }

    @PutMapping(path = "/refund/update/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public RefundVisaDto update(@PathVariable String id, @RequestBody RefundVisaDto refundVisaDto) throws StripeException {
        service.update(id, refundVisaDto);
        return null;
    }

    @GetMapping(path = "/refund/getAll/{limit}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String>  capture(@PathVariable Integer limit) throws StripeException {
        try {
            RefundCollection collection = service.getAll(limit);
            return new ResponseEntity<>(collection.toJson(), HttpStatus.OK);
        } catch (StripeException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
