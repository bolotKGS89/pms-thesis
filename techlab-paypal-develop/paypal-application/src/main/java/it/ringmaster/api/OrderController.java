package it.ringmaster.api;


import it.ringmaster.PaymentDto;
import it.ringmaster.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@Slf4j
@RestController
@RequestMapping("/api/paypal/order")
public class OrderController {
    @Autowired
    private OrderService orderService;


    @PostMapping(path = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public PaymentDto create(@RequestBody PaymentDto paymentDto) throws UnsupportedEncodingException {
        String response = orderService.createOrder();
        System.out.println(response);
        return null;
    }

    @GetMapping(path = "/retrieve/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PaymentDto retrieve(@PathVariable String id) {
        String response = orderService.retrieveOrder(id);
        System.out.println(response);
        return null;
    }

    @PostMapping(path = "/capture/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PaymentDto capture(@PathVariable String id) {
        String response = orderService.captureOrder(id);
        System.out.println(response);
        return null;
    }

    @PostMapping(path = "/confirm/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PaymentDto confirm(@PathVariable String id) {
        String response = orderService.confirmOrder(id);
        System.out.println(response);
        return null;
    }
}
