package it.ringmaster.api;

import it.ringmaster.PaymentDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/pms/amazon-pay/payments")
@AllArgsConstructor
public class PaymentController {

    @PostMapping(path = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public PaymentDto create(@RequestBody PaymentDto paymentDto) {
        return null;
    }

    @PostMapping(path = "/capture", produces = MediaType.APPLICATION_JSON_VALUE)
    public PaymentDto capture(@RequestBody PaymentDto paymentDto) {
        return null;
    }

    @PostMapping(path = "/cancel", produces = MediaType.APPLICATION_JSON_VALUE)
    public PaymentDto cancel(@RequestBody PaymentDto paymentDto) {
        return null;
    }

    @PostMapping(path = "/refund", produces = MediaType.APPLICATION_JSON_VALUE)
    public PaymentDto refund(@RequestBody PaymentDto paymentDto) {
        return null;
    }
}
