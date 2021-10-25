package com.mikhailkarpov.springcloudcontractdemo.producer.messaging;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

@RequiredArgsConstructor
public class UserEventPublisherImpl implements UserEventPublisher {

    private final RabbitTemplate rabbitTemplate;
    private final String exchange;
    private final String routingKey;

    @Override
    public void publish(UserCreatedMessage message) {
        this.rabbitTemplate.convertAndSend(exchange, routingKey, message);
    }
}
