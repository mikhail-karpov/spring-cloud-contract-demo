package com.mikhailkarpov.springcloudcontractdemo.producer.messaging;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserCreatedMessage {

    private Long userId;
    private String username;
}
