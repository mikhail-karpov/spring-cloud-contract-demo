package com.mikhailkarpov.springcloudcontractdemo.producer.api;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateUserRequest {

    private String name;
}
