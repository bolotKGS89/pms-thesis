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
@RequestMapping("/paypal")
@AllArgsConstructor
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private JsonService jsonService;

    // create order
    @PostMapping(path = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> create(@RequestHeader(value = "X-Request-ID", required = false) String xRequestId,
                                         @RequestHeader(value = "Service-Name", required = false) String serviceName,
                                         @RequestBody PaymentDto paymentDto)  {
        String json = jsonService.export(paymentDto.getIntent(), paymentDto.getTotal(), paymentDto.getCurrency());

        return new ResponseEntity<>(orderService.createOrder(json, serviceName, xRequestId).getBody(),
                orderService.createOrder(json, serviceName, xRequestId).getStatusCode());
    }

    // retrieve order details
    @GetMapping(path = "/retrieve/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> retrieve(@RequestHeader(value = "X-Request-ID", required = false) String xRequestId,
                                           @RequestHeader(value = "Service-Name", required = false) String serviceName,
                                           @PathVariable String id) {
        return new ResponseEntity<>(orderService.retrieveOrder(id, serviceName, xRequestId).getBody(),
                orderService.retrieveOrder(id, serviceName, xRequestId).getStatusCode());
    }

    // capture by id
    @GetMapping(path = "/capture/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> capture(@RequestHeader(value = "X-Request-ID", required = false) String xRequestId,
                                          @RequestHeader(value = "Service-Name", required = false) String serviceName,
                                          @PathVariable String id) {
        return new ResponseEntity<>(
                orderService.captureOrder(id, serviceName, xRequestId).getBody(),
                orderService.captureOrder(id, serviceName, xRequestId).getStatusCode());
    }

    // refund by id
    @PostMapping(path = "/refund/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> refund(@RequestHeader(value = "X-Request-ID", required = false) String xRequestId,
                                         @RequestHeader(value = "Service-Name", required = false) String serviceName,
                                         @PathVariable String id, @RequestBody PaymentDto paymentDto) {
        String json = jsonService.createRefund(paymentDto.getTotal(), paymentDto.getCurrency(), paymentDto.getNote());
        return new ResponseEntity<>(
                orderService.refundPayment(id, json, serviceName, xRequestId).getBody(),
                orderService.refundPayment(id, json, serviceName, xRequestId).getStatusCode());
    }

    // authorize by id
    @GetMapping(path = "/authorize/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> authorize(@RequestHeader(value = "X-Request-ID", required = false) String xRequestId,
                                            @RequestHeader(value = "Service-Name", required = false) String serviceName,
                                            @PathVariable String id) {
        return new ResponseEntity<>(orderService.authorizeOrder(id, serviceName, xRequestId).getBody(),
                                    orderService.authorizeOrder(id, serviceName, xRequestId).getStatusCode());
    }

    // void authorized payment by id
    @GetMapping(path = "/void/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> voidPayment(@RequestHeader(value = "X-Request-ID", required = false) String xRequestId,
                                              @RequestHeader(value = "Service-Name", required = false) String serviceName,
                                              @PathVariable String id) {
        return new ResponseEntity<>(orderService.voidPayment(id, serviceName, xRequestId).getBody(),
                orderService.voidPayment(id, serviceName, xRequestId).getStatusCode());
    }

}
