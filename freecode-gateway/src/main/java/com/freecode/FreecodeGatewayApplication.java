package com.freecode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
@EnableAutoConfiguration
public class FreecodeGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(FreecodeGatewayApplication.class, args);
    }

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p
                        .path("/user/**")
                        .filters(f -> f.addRequestParameter("param", "ak"))
                        .uri("lb://consul-consumer-web/"))
                .build();

    }
}
