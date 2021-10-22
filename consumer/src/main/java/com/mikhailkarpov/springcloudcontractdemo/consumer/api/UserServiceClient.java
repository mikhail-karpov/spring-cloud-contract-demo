package com.mikhailkarpov.springcloudcontractdemo.consumer.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@RequiredArgsConstructor
public class UserServiceClient {

    private final RestTemplate restTemplate;
    private final String clientHost;
    private final Integer clientPort;

    public Optional<User> findUserById(Long id) {
        String uri = String.format("%s:%d/users/%d", clientHost, clientPort, id);

        try {
            User user = this.restTemplate.getForObject(uri, User.class);
            return Optional.of(user);

        } catch (RestClientException e) {
            return Optional.empty();
        }
    }
}
