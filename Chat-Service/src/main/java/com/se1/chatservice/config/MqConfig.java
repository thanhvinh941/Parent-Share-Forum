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
    public static final String CHAT_QUEUE = "Chat-Queue";
    public static final String CHAT_EXCHANGE = "Chat-Exchange";
    public static final String CHAT_ROUTING_KEY  = "Chat-Routing-Key";

    @Bean
    public Queue queue() {
        return new Queue(CHAT_QUEUE);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(CHAT_EXCHANGE);
    }

    @Bean
    public Binding bindings(Queue queue, TopicExchange topicExchange) {
        return BindingBuilder
                .bind(queue)
                .to(topicExchange)
                .with(CHAT_ROUTING_KEY);
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
