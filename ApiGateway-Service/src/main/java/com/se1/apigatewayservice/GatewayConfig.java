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
								.uri("http://localhost:8088"))
				.route("author-service",
						r -> r.path("/author-item/**")
								.filters(f -> f.rewritePath("/(?<path>.*)", "/$\\{path}").filter(filter))
								.uri("http://localhost:8088"))
				.route("group-role-service",
						r -> r.path("/group-role/**")
								.filters(f -> f.rewritePath("/(?<path>.*)", "/$\\{path}").filter(filter))
								.uri("http://localhost:8088"))
				.route("contact-service",
						r -> r.path("/contact/**")
								.filters(f -> f.rewritePath("/(?<path>.*)", "/$\\{path}").filter(filter))
								.uri("http://localhost:8088"))
				.route("rating-service",
						r -> r.path("/rating/**")
								.filters(f -> f.rewritePath("/(?<path>.*)", "/$\\{path}").filter(filter))
								.uri("http://localhost:8088"))
				.route("subscriber-service",
						r -> r.path("/subscriber/**")
								.filters(f -> f.rewritePath("/(?<path>.*)", "/$\\{path}").filter(filter))
								.uri("http://localhost:8088"))
				.route("verification-service",
						r -> r.path("/verify/**")
								.filters(f -> f.rewritePath("/(?<path>.*)", "/$\\{path}").filter(filter))
								.uri("http://localhost:8088"))
				.route("auth-service",
						r -> r.path("/auth/**").filters(f -> f.filter(filter)).uri("http://localhost:8089"))
				.route("post-service",
						r -> r.path("/post/**").filters(f -> f.filter(filter)).uri("http://localhost:8082"))
				.route("topic-tag-service",
						r -> r.path("/topic-tag/**").filters(f -> f.filter(filter)).uri("http://localhost:8082"))
				.route("comment-service",
						r -> r.path("/comment/**").filters(f -> f.filter(filter)).uri("http://localhost:8082"))
				.route("system-service",
						r -> r.path("/system/**").filters(f -> f.filter(filter)).uri("http://localhost:8081"))
				.route("notify-service",
						r -> r.path("/notify/**").filters(f -> f.filter(filter)).uri("http://localhost:8083"))
				.route("chat-service",
						r -> r.path("/chat/**").filters(f -> f.filter(filter)).uri("http://localhost:8084"))
				.build();
	}

}
