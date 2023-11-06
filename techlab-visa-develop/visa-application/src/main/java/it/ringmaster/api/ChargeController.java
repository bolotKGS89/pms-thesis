package it.ringmaster.api;


import com.stripe.exception.*;
import com.stripe.model.Charge;
import it.ringmaster.PaymentVisaDto;
import it.ringmaster.service.ChargeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
    public PaymentVisaDto create(@RequestBody PaymentVisaDto paymentDto) throws StripeException {
        paymentDto.setDescription("Example charge");
        paymentDto.setCurrency(PaymentVisaDto.Currency.EUR);
        Charge charge = service.charge(paymentDto);
        return null;
    }

    @GetMapping(path = "/retrieve/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PaymentVisaDto retrieve(@PathVariable String id) throws StripeException {
        Charge charge = service.retrieve(id);
        return null;
    }

    @PutMapping(path = "/update/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PaymentVisaDto update(@PathVariable String id, @RequestBody PaymentVisaDto paymentDto) throws StripeException {
        service.update(id, paymentDto);
        return null;
    }

    @GetMapping(path = "/capture/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PaymentVisaDto capture(@PathVariable String id) throws StripeException {
        Charge charge =
                Charge.retrieve(id);

        Charge updatedCharge = charge.capture();
        return null;
    }

    @GetMapping(path = "/getAll/{limit}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PaymentVisaDto> capture(@PathVariable Integer limit) throws StripeException {
        service.getAll(limit);
        return null;
    }
}
