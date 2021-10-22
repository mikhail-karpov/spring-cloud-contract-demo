package com.mikhailkarpov.springcloudcontractdemo.producer.api;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class ContractBaseTestClass {

    @MockBean
    private UserService userService;

    @Autowired
    private UserController userController;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.standaloneSetup(MockMvcBuilders.standaloneSetup(userController));

        User user = new User(2L, "username");

        when(this.userService.create("username")).thenReturn(user);
        when(this.userService.findById(1L)).thenReturn(Optional.empty());
        when(this.userService.findById(2L)).thenReturn(Optional.of(user));
        when(this.userService.findAll()).thenReturn(Collections.singletonList(user));
    }
}
