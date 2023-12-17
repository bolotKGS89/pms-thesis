package it.ringmaster;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class PmsServerApplication {
    public static void main(String[] args) {

        SpringApplication.run(PmsServerApplication.class, args);
    }


}