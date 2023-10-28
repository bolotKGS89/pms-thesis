package it.ringmaster.api;

import it.ringmaster.WebhookDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/pms/amazon-pay/webhooks")
@AllArgsConstructor
public class WebhookController {

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public WebhookDto getWebhook() {
        return null;
    }
}
