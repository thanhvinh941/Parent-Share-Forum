package com.se1.userservice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqConfig {
	public static final String Notification_QUEUE = "Notification-Queue";
    public static final String Notification_EXCHANGE = "Notification-Exchange";
    public static final String Notification_ROUTING_KEY = "Notification-Routing-Key";
    public static final String USER_EXCHANGE = "User-Exchange";
	public static final String USER_QUEUE = "User-Queue";
	public static final String USER_ROUTING_KEY = "User-Routing-Key";

    @Bean
    public Queue queueNotification() {
        return new Queue(Notification_QUEUE);
    }

    @Bean
    public TopicExchange exchangeNotification() {
        return new TopicExchange(Notification_EXCHANGE);
    }

    @Bean
    public Binding bindingsNotification() {
        return BindingBuilder
                .bind(queueNotification())
                .to(exchangeNotification())
                .with(Notification_ROUTING_KEY);
    }
    
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
