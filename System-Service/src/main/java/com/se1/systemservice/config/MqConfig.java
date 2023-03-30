package com.se1.systemservice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqConfig {
	public static final String NOTIFY_QUEUE = "Notification-Queue";
	public static final String NOTIFY_EXCHANGE = "Notification-Exchange";
	public static final String NOTIFY_ROUTING_KEY = "Notification-Routing-Key";
    public static final String NOTIFY_CREATE_QUEUE = "Notification-Create-Queue";
    public static final String NOTIFY_CREATE_ROUTING_KEY = "Notification-Create-Routing-Key";
	public static final String CHAT_QUEUE_CREATE = "Chat-Queue-Create";
	public static final String CHAT_QUEUE_UPDATE = "Chat-Queue-Create";
	public static final String CHAT_EXCHANGE = "Chat-Exchange";
	public static final String CHAT_ROUTING_KEY_CREATE = "Chat-Routing-Key-Create";
	public static final String CHAT_ROUTING_KEY_UPDATE = "Chat-Routing-Key-Update";
	public static final String USER_EXCHANGE = "User-Exchange";
	public static final String USER_QUEUE = "User-Queue";
	public static final String USER_ROUTING_KEY = "User-Routing-Key";

	//-- CHAT ---
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

	//-- USER ---
	@Bean
	public TopicExchange exchangeUser() {
		return new TopicExchange(USER_EXCHANGE);
	}
	
	@Bean
	public Queue queueUser() {
		return new Queue(USER_QUEUE);
	}
	
	@Bean
	public Binding bindingsUser() {
		return BindingBuilder.bind(queueUser()).to(exchangeUser()).with(USER_ROUTING_KEY);
	}

	//-- NOTIFY ---
	@Bean
	public TopicExchange exchangeNotify() {
		return new TopicExchange(NOTIFY_EXCHANGE);
	}
	
	@Bean
	public Queue queueNotify() {
		return new Queue(NOTIFY_QUEUE);
	}
	
	@Bean
	public Binding bindingsNotify() {
		return BindingBuilder.bind(queueNotify()).to(exchangeNotify()).with(NOTIFY_ROUTING_KEY);
	}
	
	@Bean
    public Queue queueCreateNotify() {
        return new Queue(NOTIFY_CREATE_QUEUE);
    }
    
    @Bean
    public Binding bindingsCreateNotify() {
        return BindingBuilder
                .bind(queueCreateNotify())
                .to(exchangeNotify())
                .with(NOTIFY_CREATE_ROUTING_KEY);
    }
	
	//SYSTEM
	public static final String SYSTEM_EXCHANGE = "System-Exchange";
	public static final String SYSTEM_QUEUE = "System-Queue";
	public static final String SYSTEM_ROUTING_KEY = "System-Routing-Key";
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
                .with(SYSTEM_EXCHANGE);
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
