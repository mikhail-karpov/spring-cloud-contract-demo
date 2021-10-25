package com.mikhailkarpov.springcloudcontractdemo.consumer.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mikhailkarpov.springcloudcontractdemo.consumer.api.RabbitMQMessageListener;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitMQConfig {

    public static final String EXCHANGE = "users-exchange";
    public static final String QUEUE = "users-queue";
    public static final String KEY = "user.created";

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter(new ObjectMapper());
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory cf,
                                                                               Jackson2JsonMessageConverter jackson2JsonMessageConverter) {
        SimpleRabbitListenerContainerFactory containerFactory = new SimpleRabbitListenerContainerFactory();
        containerFactory.setConnectionFactory(cf);
        containerFactory.setMessageConverter(jackson2JsonMessageConverter);
        return containerFactory;
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
    public RabbitMQMessageListener eventListener() {
        return new RabbitMQMessageListener();
    }
}
