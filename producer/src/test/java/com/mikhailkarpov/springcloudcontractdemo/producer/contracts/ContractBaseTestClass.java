package com.mikhailkarpov.springcloudcontractdemo.producer.contracts;

import com.mikhailkarpov.springcloudcontractdemo.producer.controllers.UserController;
import com.mikhailkarpov.springcloudcontractdemo.producer.domain.User;
import com.mikhailkarpov.springcloudcontractdemo.producer.messaging.UserCreatedMessage;
import com.mikhailkarpov.springcloudcontractdemo.producer.messaging.UserEventPublisher;
import com.mikhailkarpov.springcloudcontractdemo.producer.services.UserService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest(properties = {
        "stubrunner.amqp.enabled=true" +
                "",
        "stubrunner.amqp.mockConnection=false"
})
@AutoConfigureMessageVerifier
@Testcontainers
public class ContractBaseTestClass {

    @Container
    static RabbitMQContainer RABBIT_MQ = new RabbitMQContainer("rabbitmq");

    @DynamicPropertySource
    static void configRabbitMQ(DynamicPropertyRegistry registry) {
        registry.add("spring.rabbitmq.port", RABBIT_MQ::getAmqpPort);
    }

    @MockBean
    private UserService userService;

    @Autowired
    private UserController userController;

    @Autowired
    private UserEventPublisher userEventPublisher;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.standaloneSetup(MockMvcBuilders.standaloneSetup(userController));

        User user = new User(2L, "username");

        when(this.userService.create("username")).thenReturn(user);
        when(this.userService.findById(1L)).thenReturn(Optional.empty());
        when(this.userService.findById(2L)).thenReturn(Optional.of(user));
        when(this.userService.findAll()).thenReturn(Collections.singletonList(user));
    }

    public void createUser() {
        UserCreatedMessage message = new UserCreatedMessage();
        message.setUserId(3L);
        message.setUsername("user");

        this.userEventPublisher.publish(message);
    }
}
