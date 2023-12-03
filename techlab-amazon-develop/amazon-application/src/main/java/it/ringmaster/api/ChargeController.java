package it.ringmaster.api;

import it.ringmaster.service.AmazonPayService;
import it.ringmaster.service.JsonService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/v1/charge")
@AllArgsConstructor
public class ChargeController {

    @Autowired
    private AmazonPayService amazonPayService;

    @Autowired
    private JsonService jsonService;


}
