package com.example.user_service.kafka;

import com.example.expense_tracker.common.UserCreatedEvent;
import com.example.user_service.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class KafkaProducer {

    public static final String TOPIC_NAME = "user-created";
    private final KafkaTemplate<String, UserCreatedEvent> kafkaTemplate;

    public void publishUserCreatedEvent(User user) {
        UserCreatedEvent event = new UserCreatedEvent(
                UUID.randomUUID().toString(),
                user.getUserId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName()
        );
        kafkaTemplate.send(TOPIC_NAME, user.getUserId(), event);
    }
}
