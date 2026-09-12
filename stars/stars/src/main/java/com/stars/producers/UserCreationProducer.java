package com.stars.producers;

import org.apache.kafka.common.Uuid;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.stars.domain.entities.UserEntity;

import lombok.RequiredArgsConstructor;

@Component 
@RequiredArgsConstructor
public class UserCreationProducer {

    private final KafkaTemplate<String, UserEntity> kafkaTemplate;

    public void sendUserCreationEvent(UserEntity user) {
        kafkaTemplate.send("user-topic", Uuid.randomUuid().toString(), user);
    }
    
}
