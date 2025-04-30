package com.example.user_service.kafka;


import com.example.expense_tracker.common.TransactionCreatedEvent;
import com.example.expense_tracker.common.UserCreatedEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {


    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;




    @Bean
    public <T> ConsumerFactory<String, T> consumerFactory(Class<T> eventType, String groupId) {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);

        JsonDeserializer<T> deserializer = new JsonDeserializer<>(eventType);
        deserializer.addTrustedPackages("*");

        return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(), deserializer);
    }


    @Bean(name = "userCreatedEventKafkaListenerContainerFactory")
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, UserCreatedEvent>> userCreatedEventKafkaListenerContainerFactory(){
        ConcurrentKafkaListenerContainerFactory<String, UserCreatedEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory(UserCreatedEvent.class, "account-service"));
        return factory;
    }

    @Bean(name = "transactionCreatedEventKafkaListenerContainerFactory")
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, TransactionCreatedEvent>> transactionCreatedEventKafkaListenerContainerFactory(){
        ConcurrentKafkaListenerContainerFactory<String, TransactionCreatedEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory(TransactionCreatedEvent.class, "transaction-service"));
        return factory;
    }

}
