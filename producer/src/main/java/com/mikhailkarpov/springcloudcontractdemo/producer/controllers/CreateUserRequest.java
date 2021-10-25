package com.mikhailkarpov.springcloudcontractdemo.producer.controllers;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateUserRequest {

    private String name;
}
