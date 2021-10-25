package com.mikhailkarpov.springcloudcontractdemo.consumer.api;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.StubTrigger;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.NONE,
        properties = {
                "stubrunner.amqp.enabled=true",
                "stubrunner.amqp.mockConnection=false"
        }
)
@AutoConfigureStubRunner(
        stubsMode = StubRunnerProperties.StubsMode.LOCAL,
        ids = "com.mikhailkarpov.springcloudcontractdemo:producer"
)
@Testcontainers
class RabbitMQMessageListenerTest {

    @Container
    private static final RabbitMQContainer RABBIT_MQ = new RabbitMQContainer("rabbitmq");

    @DynamicPropertySource
    static void configRabbitMQ(DynamicPropertyRegistry registry) {
        registry.add("spring.rabbitmq.port", RABBIT_MQ::getAmqpPort);
    }

    @Autowired
    private RabbitMQMessageListener eventListener;

    @Autowired
    private StubTrigger stubTrigger;

    @Test
    void whenMessageReceived_thenCounterIncremented() {
        //when
        stubTrigger.trigger("user.created.event");

        //then
        Awaitility.await().untilAsserted(() -> {
            User lastCreatedUser = eventListener.getLastCreatedUser();

            assertNotNull(lastCreatedUser);
            assertNotNull(lastCreatedUser.getId());
            assertNotNull(lastCreatedUser.getName());
        });
    }
}