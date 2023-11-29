package it.ringmaster.api;


import it.ringmaster.dtos.PaymentDto;
import it.ringmaster.service.JsonService;
import it.ringmaster.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/v1/paypal")
@AllArgsConstructor
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private JsonService jsonService;

    // create order
    @PostMapping(path = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> create(@RequestBody PaymentDto paymentDto)  {
        String json = jsonService.export(paymentDto.getIntent(), paymentDto.getTotal(), paymentDto.getCurrency());
        return new ResponseEntity<>(orderService.createOrder(json).getBody(),
                orderService.createOrder(json).getStatusCode());
    }

    // retrieve order details
    @GetMapping(path = "/retrieve/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> retrieve(@PathVariable String id) {
        return new ResponseEntity<>(orderService.retrieveOrder(id).getBody(),
                orderService.retrieveOrder(id).getStatusCode());
    }

    // capture by id
    @PostMapping(path = "/capture/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> capture(@PathVariable String id) {
        return new ResponseEntity<>(
                orderService.captureOrder(id).getBody(),
                orderService.captureOrder(id).getStatusCode());
    }

    // refund by id
    @PostMapping(path = "/refund/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> refund(@PathVariable String id, @RequestBody PaymentDto paymentDto) {
        String json = jsonService.createRefund(paymentDto.getTotal(), paymentDto.getCurrency(), paymentDto.getNote());
        return new ResponseEntity<>(
                orderService.refundPayment(id, json).getBody(),
                orderService.refundPayment(id, json).getStatusCode());
    }

}
