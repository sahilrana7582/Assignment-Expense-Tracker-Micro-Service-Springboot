package com.example.user_service.kafka;


import com.example.expense_tracker.common.TransactionCreatedEvent;
import com.example.user_service.model.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class KafkaProducer {

    public static final String TOPIC_NAME = "transaction-events";
    private final KafkaTemplate<String, TransactionCreatedEvent> kafkaTemplate;

    public void publishUserCreatedEvent(Transaction transaction) {

        TransactionCreatedEvent event = new TransactionCreatedEvent(
                UUID.randomUUID().toString(),
                transaction.getUserId(),
                transaction.getType().toString(),
                transaction.getAmount()
        );

        kafkaTemplate.send(TOPIC_NAME, transaction.getUserId(), event);
    }

}
