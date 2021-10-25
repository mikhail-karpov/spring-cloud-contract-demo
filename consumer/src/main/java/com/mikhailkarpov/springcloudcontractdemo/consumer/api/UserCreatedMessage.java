package com.mikhailkarpov.springcloudcontractdemo.consumer.api;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserCreatedMessage {

    private Long userId;
    private String username;
}
