package it.ringmaster.api;


import com.stripe.exception.*;
import com.stripe.model.PaymentIntent;
import com.stripe.model.PaymentIntentCollection;
import it.ringmaster.PaymentVisaDto;
import it.ringmaster.ResponseDto;
import it.ringmaster.service.PaymentIntentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/v1/visa")
@AllArgsConstructor
public class PaymentController {

    @Autowired
    private PaymentIntentService service;

    @PostMapping(path = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDto> create(@RequestBody PaymentVisaDto paymentDto) {
        try {
            PaymentIntent paymentIntent = service.create(paymentDto);
            ResponseDto responseDto = new ResponseDto();
            responseDto.setId(paymentIntent.getId());
            responseDto.setAmount(paymentIntent.getAmount());
            responseDto.setCreated(paymentIntent.getCreated());
            responseDto.setCurrency(paymentIntent.getCurrency());
            responseDto.setDescription(paymentIntent.getDescription());
            return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
        } catch (StripeException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/retrieve/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDto> retrieve(@PathVariable String id) {
        try {
            PaymentIntent paymentIntent = service.retrieve(id);
            ResponseDto responseDto = new ResponseDto();
            responseDto.setId(paymentIntent.getId());
//            responseDto.setBalanceTransaction(paymentIntent.getBalanceTransaction());
            responseDto.setAmount(paymentIntent.getAmount());
            responseDto.setCreated(paymentIntent.getCreated());
            responseDto.setCurrency(paymentIntent.getCurrency());
            responseDto.setDescription(paymentIntent.getDescription());
            return new ResponseEntity<>(responseDto, HttpStatus.FOUND);
        } catch (StripeException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(path = "/update/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDto> update(@PathVariable String id, @RequestBody PaymentVisaDto paymentDto) {
        try {
            PaymentIntent paymentIntent = service.update(id, paymentDto);
            ResponseDto responseDto = new ResponseDto();
            responseDto.setId(paymentIntent.getId());
//            responseDto.setBalanceTransaction(paymentIntent.getBalanceTransaction());
            responseDto.setAmount(paymentIntent.getAmount());
            responseDto.setCreated(paymentIntent.getCreated());
            responseDto.setCurrency(paymentIntent.getCurrency());
            responseDto.setDescription(paymentIntent.getDescription());
            return new ResponseEntity<>(responseDto, HttpStatus.ACCEPTED);
        } catch (StripeException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/capture/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDto> capture(@PathVariable String id) {
        try {
            PaymentIntent paymentIntent = service.capture(id);
            ResponseDto responseDto = new ResponseDto();
            responseDto.setId(paymentIntent.getId());
//            responseDto.setBalanceTransaction(paymentIntent.getBalanceTransaction());
            responseDto.setAmount(paymentIntent.getAmount());
            responseDto.setCreated(paymentIntent.getCreated());
            responseDto.setCurrency(paymentIntent.getCurrency());
            responseDto.setDescription(paymentIntent.getDescription());
            return new ResponseEntity<>(responseDto, HttpStatus.ACCEPTED);
        } catch (StripeException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/getAll/{limit}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getLimit(@PathVariable Integer limit)  {
        try {
            PaymentIntentCollection collection = service.getAll(limit);
            return new ResponseEntity<>(collection.toJson(), HttpStatus.OK);
        } catch (StripeException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
