package com.mikhailkarpov.springcloudcontractdemo.consumer.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class RabbitMQMessageListener {

    private User lastCreatedUser;

    @RabbitListener(queues = "users-queue")
    public void process(UserCreatedMessage message) {
        log.info("User created: {}", message);
        User user = new User(message.getUserId(), message.getUsername());
        this.lastCreatedUser = user;
    }

    public User getLastCreatedUser() {
        return lastCreatedUser;
    }
}
