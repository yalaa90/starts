package com.stars.consumers;

import java.util.List;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.stars.domain.entities.UserEntity;
import com.stars.services.UserService;

import lombok.RequiredArgsConstructor;

@Component 
@RequiredArgsConstructor
public class UserCerationConsumer {
    
    private final UserService userService;

    @KafkaListener (topics = "user-topic")
    public void consumeUserCreationEvent(List<UserEntity> users) {
        userService.saveUsers(users);
    }
}
