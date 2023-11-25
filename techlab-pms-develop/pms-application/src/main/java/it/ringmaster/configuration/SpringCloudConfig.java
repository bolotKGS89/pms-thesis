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
                        .filters(f -> f.setPath("/v1/paypal/create"))
                        .uri("http://techlab-paypal-develop:8081")
                )
                .route(p ->
                        p.path("/paypal/retrieve/{id}")
                                .filters(f -> f.setPath("/v1/paypal/retrieve/{id}"))
                                .uri("http://techlab-paypal-develop:8081")
                )
                .route(p ->
                        p.path("/paypal/capture/{id}")
                                .filters(f -> f.setPath("/v1/paypal/capture/{id}"))
                                .uri("http://techlab-paypal-develop:8081")
                )
                .route(p ->
                        p.path("/paypal/refund/{id}")
                                .filters(f -> f.setPath("/v1/paypal/refund/{id}"))
                                .uri("http://techlab-paypal-develop:8081")
                )
                .route(p ->
                        p.path("/visa/create")
                        .filters(f -> f.setPath("/v1/visa/charge/create"))
                        .uri("http://techlab-visa-develop:8082")
                )
                .route(p ->
                        p.path("/visa/retrieve/{id}")
                                .filters(f -> f.setPath("/v1/visa/charge/retrieve/{id}"))
                                .uri("http://techlab-visa-develop:8082")
                )
                .route(p ->
                        p.path("/visa/update/{id}")
                                .filters(f -> f.setPath("/v1/visa/charge/update/{id}"))
                                .uri("http://techlab-visa-develop:8082")
                )
                .route(p ->
                        p.path("/visa/capture/{id}")
                                .filters(f -> f.setPath("/v1/visa/charge/capture/{id}"))
                                .uri("http://techlab-visa-develop:8082")
                )
                .route(p ->
                        p.path("/visa/charge/getAll/{limit}")
                                .filters(f -> f.setPath("/v1/visa/charge/getAll/{limit}"))
                                .uri("http://techlab-visa-develop:8082")
                )
                .route(p ->
                        p.path("/visa/balance")
                                .filters(f -> f.setPath("/v1/visa/balance/retrieve"))
                                .uri("http://techlab-visa-develop:8082")
                )
                .route(p ->
                        p.path("/visa/refund")
                                .filters(f -> f.setPath("/v1/visa/refund/create"))
                                .uri("http://techlab-visa-develop:8082")
                )
                .route(p ->
                        p.path("/visa/refund/getAll/{limit}")
                                .filters(f -> f.setPath("/v1/visa/refund/getAll/{limit}"))
                                .uri("http://techlab-visa-develop:8082")
                )
                .build();
    }
}
