package com.se1.chatservice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqConfig {
	public static final String CHAT_QUEUE_CREATE = "Chat-Queue-Create";
	public static final String CHAT_QUEUE_UPDATE = "Chat-Queue-Update";
	public static final String CHAT_EXCHANGE = "Chat-Exchange";
	public static final String CHAT_ROUTING_KEY_CREATE = "Chat-Routing-Key-Create";
	public static final String CHAT_ROUTING_KEY_UPDATE = "Chat-Routing-Key-Update";
	public static final String SYSTEM_EXCHANGE = "System-Exchange";
	public static final String SYSTEM_QUEUE = "System-Queue";
	public static final String SYSTEM_ROUTING_KEY = "System-Routing-Key";
	
	// -- CHAT ---
	@Bean
	public TopicExchange exchangeChat() {
		return new TopicExchange(CHAT_EXCHANGE);
	}

	@Bean
	public Queue queueChatCreate() {
		return new Queue(CHAT_QUEUE_CREATE);
	}

	@Bean
	public Queue queueChatUpdate() {
		return new Queue(CHAT_QUEUE_UPDATE);
	}

	@Bean
	public Binding bindingsChatCreate() {
		return BindingBuilder.bind(queueChatCreate()).to(exchangeChat()).with(CHAT_ROUTING_KEY_CREATE);
	}

	@Bean
	public Binding bindingsChatUpdate() {
		return BindingBuilder.bind(queueChatUpdate()).to(exchangeChat()).with(CHAT_ROUTING_KEY_UPDATE);
	}
	// -- CHAT ---

	//SYSTEM
    @Bean
    public Queue queueSystem() {
        return new Queue(SYSTEM_QUEUE);
    }

    @Bean
    public TopicExchange exchangeSystem() {
        return new TopicExchange(SYSTEM_EXCHANGE);
    }

    @Bean
    public Binding bindingsSystem() {
        return BindingBuilder
                .bind(queueSystem())
                .to(exchangeSystem())
                .with(SYSTEM_ROUTING_KEY);
    }
	@Bean
	public MessageConverter jsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	@Bean
	public AmqpTemplate notificationRabbitTemplate(ConnectionFactory connectionFactory) {
		final RabbitTemplate template = new RabbitTemplate(connectionFactory);
		template.setMessageConverter(jsonMessageConverter());
		return template;
	}
}
