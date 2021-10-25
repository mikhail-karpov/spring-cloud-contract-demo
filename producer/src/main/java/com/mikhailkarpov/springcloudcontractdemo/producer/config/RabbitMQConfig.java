package com.mikhailkarpov.springcloudcontractdemo.producer.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mikhailkarpov.springcloudcontractdemo.producer.messaging.UserEventPublisher;
import com.mikhailkarpov.springcloudcontractdemo.producer.messaging.UserEventPublisherImpl;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE = "users-exchange";
    public static final String QUEUE = "users-queue";
    public static final String KEY = "user.created";

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter(new ObjectMapper());
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory cf,
                                         Jackson2JsonMessageConverter jackson2JsonMessageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(cf);
        rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter);
        return rabbitTemplate;
    }

    @Bean
    public Exchange userExchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Queue userEventQueue() {
        return new Queue(QUEUE);
    }

    @Bean
    public Binding userEventQueueBinding(TopicExchange userExchange, Queue userEventQueue) {
        return BindingBuilder
                .bind(userEventQueue)
                .to(userExchange)
                .with(KEY);
    }

    @Bean
    public UserEventPublisher userEventPublisher(RabbitTemplate rabbitTemplate) {
        return new UserEventPublisherImpl(rabbitTemplate, EXCHANGE, KEY);
    }
}
