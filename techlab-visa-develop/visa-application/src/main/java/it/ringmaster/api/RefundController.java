package it.ringmaster.api;

import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Refund;
import it.ringmaster.PaymentVisaDto;
import it.ringmaster.RefundVisaDto;
import it.ringmaster.service.RefundService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/v1/visa/refund")
@AllArgsConstructor
public class RefundController {

    @Autowired
    private RefundService service;

    @PostMapping(path = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public RefundVisaDto create(@RequestBody RefundVisaDto refundVisaDto) throws StripeException {
        refundVisaDto.setDescription("Example charge");
        refundVisaDto.setCurrency(RefundVisaDto.Currency.EUR);
        Refund refund = service.create(refundVisaDto);
        return null;
    }

    @GetMapping(path = "/retrieve/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public RefundVisaDto retrieve(@PathVariable String id) throws StripeException {
        service.retrieve(id);
        return null;
    }

    @PutMapping(path = "/update/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public RefundVisaDto update(@PathVariable String id, @RequestBody RefundVisaDto refundVisaDto) throws StripeException {
        service.update(id, refundVisaDto);
        return null;
    }

    @GetMapping(path = "/getAll/{limit}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RefundVisaDto> capture(@PathVariable Integer limit) throws StripeException {
        service.getAll(limit);
        return null;
    }
}
