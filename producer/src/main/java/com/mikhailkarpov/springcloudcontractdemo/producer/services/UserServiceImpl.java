package com.mikhailkarpov.springcloudcontractdemo.producer.services;

import com.mikhailkarpov.springcloudcontractdemo.producer.domain.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public User create(String name) {
        return null;
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void deleteById(Long id) {

    }
}
