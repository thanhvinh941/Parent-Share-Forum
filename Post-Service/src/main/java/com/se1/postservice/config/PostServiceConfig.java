package com.se1.postservice.config;

import java.util.TimeZone;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PostServiceConfig {

	@Bean
	public Jackson2ObjectMapperBuilderCustomizer jacksonObjectMapperCustomization() {
	    return jacksonObjectMapperBuilder -> 
	        jacksonObjectMapperBuilder.timeZone(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
	}
}
