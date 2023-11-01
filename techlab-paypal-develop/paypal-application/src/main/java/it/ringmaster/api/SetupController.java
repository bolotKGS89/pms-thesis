package it.ringmaster.api;


import it.ringmaster.PaymentDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api/pms/visa")
@AllArgsConstructor
public class SetupController {

    @PostMapping(path = "/setup", produces = MediaType.APPLICATION_JSON_VALUE)
    public PaymentDto setup(@RequestBody PaymentDto paymentDto) {
        return null;
    }

    @GetMapping(path = "/settings", produces = MediaType.APPLICATION_JSON_VALUE)
    public PaymentDto getSettings(@RequestBody PaymentDto paymentDto) {
        return null;
    }
}
