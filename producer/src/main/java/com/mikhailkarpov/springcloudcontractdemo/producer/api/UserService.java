package com.mikhailkarpov.springcloudcontractdemo.producer.api;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User create(String name);

    List<User> findAll();

    Optional<User> findById(Long id);

    void deleteById(Long id);
}
