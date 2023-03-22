package com.se1.systemservice.config;

import org.kurento.client.KurentoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebsocketMessageBrokerConfig implements WebSocketMessageBrokerConfigurer {

	@Bean
	  public CallHandler callHandler() {
	    return new CallHandler();
	  }

	  @Bean
	  public KurentoClient kurentoClient() {
	    return KurentoClient.create();
	  }
	
	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.setApplicationDestinationPrefixes("/ws/app")
				.enableSimpleBroker("/ws/topic");
	}

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/system/ws").setAllowedOriginPatterns("*").withSockJS();
	}
}
