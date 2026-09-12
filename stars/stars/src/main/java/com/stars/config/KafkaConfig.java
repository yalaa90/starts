package com.stars.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;

import com.stars.domain.entities.UserEntity;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic userTopic() {
        return TopicBuilder.name("user-topic")
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic userTopicDlt() {
        return TopicBuilder.name("user-topic.DLT")
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public DefaultErrorHandler errorHandler(KafkaTemplate<String, UserEntity> kafkaTemplate) {
        return new DefaultErrorHandler(new DeadLetterPublishingRecoverer(kafkaTemplate));
    }
}
