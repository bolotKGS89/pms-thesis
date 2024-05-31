package it.ringmaster.api;

import com.stripe.exception.StripeException;
import com.stripe.model.Balance;
import it.ringmaster.service.BalanceService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/visa")
@AllArgsConstructor
public class BalanceController {

    @Autowired
    private BalanceService service;

    @GetMapping(path = "/balance/retrieve", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> retrieve() throws StripeException {
        try {
            return new ResponseEntity<>(service.retrieveBalance().toJson(), HttpStatus.OK);
        } catch (StripeException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


}
