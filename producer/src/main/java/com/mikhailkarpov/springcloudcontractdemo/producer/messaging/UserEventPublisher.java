package com.mikhailkarpov.springcloudcontractdemo.producer.messaging;

public interface UserEventPublisher {

    void publish(UserCreatedMessage message);
}
