package com.mikhailkarpov.springcloudcontractdemo.producer.controllers;

import com.mikhailkarpov.springcloudcontractdemo.producer.domain.User;
import com.mikhailkarpov.springcloudcontractdemo.producer.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/users")
    public ResponseEntity<Object> createUser(@RequestBody CreateUserRequest request,
                                             UriComponentsBuilder uriComponentsBuilder) {

        User user = this.userService.create(request.getName());
        URI location = uriComponentsBuilder.path("/users/{id}").build(user.getId());

        return ResponseEntity.created(location).body(user);
    }

    @GetMapping("/users")
    public List<User> findAll() {
        return this.userService.findAll();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id) {
        Optional<User> optionalUser = this.userService.findById(id);

        return optionalUser.isPresent() ?
                ResponseEntity.ok(optionalUser.get()) :
                ResponseEntity.notFound().build();
    }

    @DeleteMapping("/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {

        userService.deleteById(id);
    }
}
