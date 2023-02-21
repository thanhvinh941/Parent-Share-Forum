package com.se1.apigatewayservice;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

@Configuration
public class CorsConfig extends CorsConfiguration{
	@Bean
    public CorsWebFilter corsFilter()
    {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials( true );
        config.setAllowedOrigins( List.of( "http://localhost:3000" ) );
        config.setAllowedMethods( List.of( "*" ) );
        config.setAllowedHeaders( List.of( "*" ) );

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration( "/**", config );

        return new CorsWebFilter( source );
    }
}
