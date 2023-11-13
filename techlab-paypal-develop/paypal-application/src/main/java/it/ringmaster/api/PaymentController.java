package it.ringmaster.api;

import it.ringmaster.PaymentDto;
import it.ringmaster.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@Slf4j
@RestController
@RequestMapping("/api/paypal/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping(path = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public PaymentDto create(@RequestBody PaymentDto paymentDto) throws UnsupportedEncodingException {
        String res = paymentService.makePayment();
        System.out.println(res);
        return null;
    }

    @GetMapping(path = "/retrieve/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PaymentDto retrieve(@PathVariable String id) {
        String response = paymentService.retrievePayment(id);
        return null;
    }

    @PostMapping(path = "/capture/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PaymentDto capture(@PathVariable String id) {
        String response = paymentService.capturePayment(id);
        return null;
    }

    @PostMapping(path = "/void/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PaymentDto voidPayment(@PathVariable String id) {
//        String response = paymentService.capturePayment(id);
        return null;
    }
}
