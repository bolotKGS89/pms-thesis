package it.ringmaster.api;


import it.ringmaster.PaymentDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api/pms/visa/payments")
@AllArgsConstructor
public class PaymentController {

    @PostMapping(path = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public PaymentDto create(@RequestBody PaymentDto paymentDto) {
        return null;
    }

    @PostMapping(path = "/confirm", produces = MediaType.APPLICATION_JSON_VALUE)
    public PaymentDto confirm(@RequestBody PaymentDto paymentDto) {
        return null;
    }

    @DeleteMapping(path = "/cancel", produces = MediaType.APPLICATION_JSON_VALUE)
    public PaymentDto cancel(@RequestBody PaymentDto paymentDto) {
        return null;
    }

    @PostMapping(path = "/refund", produces = MediaType.APPLICATION_JSON_VALUE)
    public PaymentDto refund(@RequestBody PaymentDto paymentDto) {
        return null;
    }
}
