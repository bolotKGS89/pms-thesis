package it.ringmaster.api.v1;

import it.ringmaster.pms.api.PaymentDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@RestController
@RequestMapping("/api/v1/pms/payment-methods")
@AllArgsConstructor
@Slf4j
public class PaymentController {

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public PaymentDto createPaymentMethod(@RequestBody PaymentDto paymentDto) {
        return null;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PaymentDto> getPaymentMethods() {
        return null;
    }

    @GetMapping(value = "/{method-id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PaymentDto getPaymentMethod() {
        return null;
    }

    @PutMapping(value = "/{method-id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PaymentDto updatePaymentMethod(@PathVariable("method-id") Integer methodId) {
        return null;
    }

    @DeleteMapping(value = "/{method-id}")
    public void  deletePaymentMethod(@PathVariable("method-id") Integer methodId) {

    }

}
