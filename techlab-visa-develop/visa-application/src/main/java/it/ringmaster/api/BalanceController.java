package it.ringmaster.api;

import com.stripe.exception.StripeException;
import com.stripe.model.Balance;
import it.ringmaster.service.BalanceService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/v1/visa/balance")
@AllArgsConstructor
public class BalanceController {

    @Autowired
    private BalanceService service;

    @GetMapping(path = "/retrieve", produces = MediaType.APPLICATION_JSON_VALUE)
    public Balance retrieve() throws StripeException {
        return service.retrieveBalance();
    }


}
