package com.se1.notifyservice.config;

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
    public static final String USER_EXCHANGE = "User-Exchange";
	public static final String USER_QUEUE = "User-Queue";
	public static final String USER_ROUTING_KEY = "User-Routing-Key";
	public static final String SYSTEM_EXCHANGE = "System-Exchange";
	public static final String SYSTEM_QUEUE = "System-Queue";
	public static final String SYSTEM_ROUTING_KEY = "System-Routing-Key";
    
	// NOTIFY
	@Bean
    public Queue queueNOTIFY() {
        return new Queue(NOTIFY_QUEUE);
    }

    @Bean
    public TopicExchange exchangeNOTIFY() {
        return new TopicExchange(NOTIFY_EXCHANGE);
    }

    @Bean
    public Binding bindingsNOTIFY() {
        return BindingBuilder
                .bind(queueNOTIFY())
                .to(exchangeNOTIFY())
                .with(NOTIFY_ROUTING_KEY);
    }
    
    @Bean
    public Queue queueCreateNotify() {
        return new Queue(NOTIFY_CREATE_QUEUE);
    }
    
    @Bean
    public Binding bindingsCreateNotify() {
        return BindingBuilder
                .bind(queueCreateNotify())
                .to(exchangeNOTIFY())
                .with(NOTIFY_CREATE_ROUTING_KEY);
    }
    
    //USER
    @Bean
    public Queue queueUser() {
        return new Queue(USER_QUEUE);
    }

    @Bean
    public TopicExchange exchangeUser() {
        return new TopicExchange(USER_EXCHANGE);
    }

    @Bean
    public Binding bindingsUser() {
        return BindingBuilder
                .bind(queueUser())
                .to(exchangeUser())
                .with(USER_ROUTING_KEY);
    }
    
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
                .with(SYSTEM_EXCHANGE);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate notifyRabbitTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }
}
