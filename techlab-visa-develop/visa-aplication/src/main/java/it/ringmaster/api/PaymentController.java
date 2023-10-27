package it.ringmaster.api;


import it.ringmaster.PaymentVisaDto;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
//@Slf4j
@RequestMapping("/api/pms/visa/payments")
@AllArgsConstructor
public class PaymentController {

    @PostMapping(path = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public PaymentVisaDto create(@RequestBody PaymentVisaDto paymentDto) {
        return null;
    }

    @PostMapping(path = "/confirm", produces = MediaType.APPLICATION_JSON_VALUE)
    public PaymentVisaDto confirm(@RequestBody PaymentVisaDto paymentDto) {
        return null;
    }

    @DeleteMapping(path = "/cancel", produces = MediaType.APPLICATION_JSON_VALUE)
    public PaymentVisaDto cancel(@RequestBody PaymentVisaDto paymentDto) {
        return null;
    }

    @PostMapping(path = "/refund", produces = MediaType.APPLICATION_JSON_VALUE)
    public PaymentVisaDto refund(@RequestBody PaymentVisaDto paymentDto) {
        return null;
    }
}
