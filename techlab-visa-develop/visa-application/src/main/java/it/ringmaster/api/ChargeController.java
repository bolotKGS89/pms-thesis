package it.ringmaster.api;


import com.stripe.exception.*;
import com.stripe.model.Charge;
import com.stripe.model.ChargeCollection;
import it.ringmaster.PaymentVisaDto;
import it.ringmaster.ResponseDto;
import it.ringmaster.service.ChargeService;
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
@RequestMapping("/v1/visa/charge")
@AllArgsConstructor
public class ChargeController {

    @Autowired
    private ChargeService service;

    @PostMapping(path = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDto> create(@RequestBody PaymentVisaDto paymentDto) {
        try {
            Charge charge = service.charge(paymentDto);
            ResponseDto responseDto = new ResponseDto();
            responseDto.setId(charge.getId());
            responseDto.setBalanceTransaction(charge.getBalanceTransaction());
            responseDto.setAmount(charge.getAmount());
            responseDto.setCreated(charge.getCreated());
            responseDto.setCurrency(charge.getCurrency());
            responseDto.setDescription(charge.getDescription());
            return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
        } catch (StripeException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/retrieve/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDto> retrieve(@PathVariable String id) {
        try {
            Charge charge = service.retrieve(id);
            ResponseDto responseDto = new ResponseDto();
            responseDto.setId(charge.getId());
            responseDto.setBalanceTransaction(charge.getBalanceTransaction());
            responseDto.setAmount(charge.getAmount());
            responseDto.setCreated(charge.getCreated());
            responseDto.setCurrency(charge.getCurrency());
            responseDto.setDescription(charge.getDescription());
            return new ResponseEntity<>(responseDto, HttpStatus.FOUND);
        } catch (StripeException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(path = "/update/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDto> update(@PathVariable String id, @RequestBody PaymentVisaDto paymentDto) {
        try {
            Charge charge = service.update(id, paymentDto);
            ResponseDto responseDto = new ResponseDto();
            responseDto.setId(charge.getId());
            responseDto.setBalanceTransaction(charge.getBalanceTransaction());
            responseDto.setAmount(charge.getAmount());
            responseDto.setCreated(charge.getCreated());
            responseDto.setCurrency(charge.getCurrency());
            responseDto.setDescription(charge.getDescription());
            return new ResponseEntity<>(responseDto, HttpStatus.ACCEPTED);
        } catch (StripeException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/capture/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDto> capture(@PathVariable String id) {
        try {
            Charge capturedCharge = service.capture(id);
            ResponseDto responseDto = new ResponseDto();
            responseDto.setId(capturedCharge.getId());
            responseDto.setBalanceTransaction(capturedCharge.getBalanceTransaction());
            responseDto.setAmount(capturedCharge.getAmount());
            responseDto.setCreated(capturedCharge.getCreated());
            responseDto.setCurrency(capturedCharge.getCurrency());
            responseDto.setDescription(capturedCharge.getDescription());
            return new ResponseEntity<>(responseDto, HttpStatus.ACCEPTED);
        } catch (StripeException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/getAll/{limit}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getLimit(@PathVariable Integer limit) throws StripeException {
        try {
            ChargeCollection collection = service.getAll(limit);
            return new ResponseEntity<>(collection.toJson(), HttpStatus.OK);
        } catch (StripeException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
