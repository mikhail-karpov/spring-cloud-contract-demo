package com.mikhailkarpov.springcloudcontractdemo.consumer.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class UserServiceClientConfig {

    @Bean
    public RestTemplate restTemplate(@Autowired RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.build();
    }

    @Bean
    public UserServiceClient userServiceClient(@Autowired RestTemplate restTemplate,
                                               @Value("${producer.host}") String clientHost,
                                               @Value("${producer.port}") Integer clientPort) {
        return new UserServiceClient(restTemplate, clientHost, clientPort);
    }
}
