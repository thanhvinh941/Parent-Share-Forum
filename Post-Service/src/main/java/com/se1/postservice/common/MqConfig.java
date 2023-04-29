package com.se1.postservice.common;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqConfig {
	public static final String SYSTEM_EXCHANGE = "System-Exchange";
	public static final String SYSTEM_QUEUE = "System-Queue";
	public static final String SYSTEM_ROUTING_KEY = "System-Routing-Key";

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
    public AmqpTemplate notificationRabbitTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }
}
