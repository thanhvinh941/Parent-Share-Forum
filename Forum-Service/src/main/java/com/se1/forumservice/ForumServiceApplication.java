package com.se1.forumservice;


import java.text.SimpleDateFormat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
@EnableDiscoveryClient
public class ForumServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ForumServiceApplication.class, args);
	}
	
	@Bean
	ObjectMapper objectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"));
		return mapper;
	}

}
