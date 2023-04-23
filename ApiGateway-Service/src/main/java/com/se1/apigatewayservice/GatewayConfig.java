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
				.route("user-service",
						r -> r.path("/user/**").filters(f -> f.rewritePath("/(?<path>.*)", "/$\\{path}").filter(filter))
								.uri("lb://user-service"))
				.route("author-service",
						r -> r.path("/author-item/**")
								.filters(f -> f.rewritePath("/(?<path>.*)", "/$\\{path}").filter(filter))
								.uri("lb://user-service"))
				.route("group-role-service",
						r -> r.path("/group-role/**")
								.filters(f -> f.rewritePath("/(?<path>.*)", "/$\\{path}").filter(filter))
								.uri("lb://user-service"))
				.route("contact-service",
						r -> r.path("/contact/**")
								.filters(f -> f.rewritePath("/(?<path>.*)", "/$\\{path}").filter(filter))
								.uri("lb://user-service"))
				.route("rating-service",
						r -> r.path("/rating/**")
								.filters(f -> f.rewritePath("/(?<path>.*)", "/$\\{path}").filter(filter))
								.uri("lb://user-service"))
				.route("verification-service",
						r -> r.path("/verify/**")
								.filters(f -> f.rewritePath("/(?<path>.*)", "/$\\{path}").filter(filter))
								.uri("lb://user-service"))
				.route("auth-service", r -> r.path("/auth/**").filters(f -> f.filter(filter)).uri("lb://auth-service"))
				.route("post-service", r -> r.path("/post/**").filters(f -> f.filter(filter)).uri("lb://post-service"))
				.route("topic-tag-service",
						r -> r.path("/topic-tag/**").filters(f -> f.filter(filter)).uri("lb://post-service"))
				.route("comment-service",
						r -> r.path("/comment/**").filters(f -> f.filter(filter)).uri("lb://post-service"))
				.route("system-service",
						r -> r.path("/system/**").filters(f -> f.filter(filter)).uri("lb://system-service"))
				.route("notify-service",
						r -> r.path("/notify/**").filters(f -> f.filter(filter)).uri("lb://notify-service"))
				.route("chat-service", r -> r.path("/chat/**").filters(f -> f.filter(filter)).uri("lb://chat-service"))
				.build();
	}

}
