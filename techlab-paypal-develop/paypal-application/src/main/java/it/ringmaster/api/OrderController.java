package it.ringmaster.api;


import it.ringmaster.PaymentDto;
import it.ringmaster.service.JsonService;
import it.ringmaster.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/paypal")
@AllArgsConstructor
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private JsonService jsonService;

    // create payment
    @PostMapping(path = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> create(@RequestBody PaymentDto paymentDto)  {
        String json = jsonService.export(paymentDto.getIntent(), paymentDto.getTotal(), paymentDto.getCurrency());
        return new ResponseEntity<>(orderService.createOrder(json).getBody(),
                orderService.createOrder(json).getStatusCode());
    }

    // retrieve payment details
    @GetMapping(path = "/retrieve/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> retrieve(@PathVariable String id) {
        return new ResponseEntity<>(orderService.retrieveOrder(id).getBody(),
                orderService.retrieveOrder(id).getStatusCode());
    }

    // execute by id
    @PostMapping(path = "/execute/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> execute(@PathVariable String id) {
        String json = jsonService.getPaymentId("C4PU98DLD4KTY");
        return new ResponseEntity<String>(
                orderService.executeOrder(id, json).getBody(),
                orderService.executeOrder(id, json).getStatusCode());
    }

    @PostMapping(path = "/confirm/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PaymentDto confirm(@PathVariable String id) {
        String response = orderService.confirmOrder(id);
        System.out.println(response);
        return null;
    }
}
