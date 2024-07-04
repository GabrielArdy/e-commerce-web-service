package com.tr.apigateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfig {

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("web_api_route", r -> r.path("/api/web/**")
                        .uri("http://localhost:9001"))
                .route("mobile_api_route", r -> r.path("/api/mobile/**")
                        .uri("http://localhost:9002"))
                .route("desktop_api_route", r -> r.path("/api/desktop/**")
                        .uri("http://localhost:9003"))
                .build();
    }
}


