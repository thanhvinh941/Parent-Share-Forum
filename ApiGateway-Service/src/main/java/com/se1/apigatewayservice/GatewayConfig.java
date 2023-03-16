package com.se1.apigatewayservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableHystrix
public class GatewayConfig {

    @Autowired
    AuthFilters filter;

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("user-service", r -> r.path("/users/**")
                        .filters(f -> f.rewritePath("/(?<path>.*)", "/$\\{path}").filter(filter))
                        .uri("lb://user-service"))
                .route("auth-service", r -> r.path("/auth/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://auth-service"))
                .route("post-service", r -> r.path("/post/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://post-service"))
                .route("system-service", r -> r.path("/system/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://system-service"))
                .route("comment-service", r -> r.path("/comments/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://comment-service"))
                .route("chat-service", r -> r.path("/chat/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://chat-service"))
                .build();
    }

}
