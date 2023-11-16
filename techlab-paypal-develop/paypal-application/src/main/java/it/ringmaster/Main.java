package it.ringmaster;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@SpringBootApplication
@RestController
@EnableDiscoveryClient
public class Main {
    @RequestMapping("/")
    public String home() {
        return "Eureka Client application";
    }

    public static void main(String[] args) {

        SpringApplication.run(Main.class, args);
    }
}