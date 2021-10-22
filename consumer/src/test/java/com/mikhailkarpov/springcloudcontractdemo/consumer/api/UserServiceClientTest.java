package com.mikhailkarpov.springcloudcontractdemo.consumer.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = {"producer.host=http://localhost", "producer.port=8090"})
@AutoConfigureStubRunner(
        stubsMode = StubRunnerProperties.StubsMode.LOCAL,
        ids = "com.mikhailkarpov.springcloudcontractdemo:producer:+:stubs:8090"
)
class UserServiceClientTest {

    @Autowired
    private UserServiceClient userServiceClient;

    @Test
    void givenUserExists_whenFindUserById_thenFound() {
        //when
        Optional<User> user = userServiceClient.findUserById(2L);

        //then
        assertTrue(user.isPresent());
        assertEquals(2L, user.get().getId());
        assertEquals("username", user.get().getName());
    }

    @Test
    void givenUserNotExists_whenFindUserById_thenEmpty() {
        //when
        Optional<User> user = userServiceClient.findUserById(1L);

        //then
        assertFalse(user.isPresent());
    }
}