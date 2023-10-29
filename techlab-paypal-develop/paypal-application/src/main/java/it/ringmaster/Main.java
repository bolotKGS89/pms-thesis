package it.ringmaster;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
public class Main {
    @RequestMapping("/")
    public String home() {
        return "Dockerizing Spring Boot Application";
    }

    public static void main(String[] args) {

        SpringApplication.run(Main.class, args);
    }
}