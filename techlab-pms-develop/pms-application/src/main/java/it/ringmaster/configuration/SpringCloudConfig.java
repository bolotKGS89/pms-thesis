package it.ringmaster.configuration;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringCloudConfig {

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p ->
                        p.path("/paypal/create")
                        .filters(f -> f.setPath("/paypal/create"))
                        .uri("http://techlab-paypal-develop:8081")
                )
                .build();
    }
}
