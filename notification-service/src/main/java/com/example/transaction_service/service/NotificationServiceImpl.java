package com.example.transaction_service.service;


import com.example.expense_tracker.common.UserCreatedEvent;
import com.example.transaction_service.dto.requestDto.NotificationRequestDTO;
import com.example.transaction_service.dto.requestDto.NotificationUpdateReadStatusDTO;
import com.example.transaction_service.dto.responseDto.NotificationResponseDTO;
import com.example.transaction_service.enums.NotificationType;
import com.example.transaction_service.exception.ResourceNotFound;
import com.example.transaction_service.mapper.NotificationMapper;
import com.example.transaction_service.model.Notification;
import com.example.transaction_service.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;

    @Override
    public NotificationResponseDTO createNotification(NotificationRequestDTO requestDTO) {
        Notification notification = notificationMapper.toEntity(requestDTO);
        Notification saved = notificationRepository.save(notification);
        return notificationMapper.toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotificationResponseDTO> getAllNotificationsByUser(String userId) {
        List<Notification> notifications = notificationRepository.findAllByUserId(userId);
        return notifications.stream()
                .map(notificationMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public NotificationResponseDTO updateReadStatus(NotificationUpdateReadStatusDTO statusDTO) {
        Notification notification = notificationRepository.findByNotificationId(statusDTO.getNotificationId())
                .orElseThrow(() -> new ResourceNotFound("Notification not found with ID: " + statusDTO.getNotificationId()));

        notification.setRead(statusDTO.getIsRead());
        Notification updated = notificationRepository.save(notification);
        return notificationMapper.toDto(updated);
    }

    @Override
    public void deleteNotification(String notificationId) {
        Notification notification = notificationRepository.findByNotificationId(notificationId)
                .orElseThrow(() -> new ResourceNotFound("Notification not found with ID: " + notificationId));
        notificationRepository.delete(notification);
    }




    @KafkaListener(
            topics = "${kafka.topic.name}",
            groupId = "${kafka.topic.group-id}",
            containerFactory = "userCreatedEventKafkaListenerContainerFactory"
    )
    public void consumeUserCreatedEvent(UserCreatedEvent event) {
        Notification notification = new Notification();
        notification.setNotificationId(UUID.randomUUID().toString());
        notification.setUserId(event.getUserId());
        notification.setMessage("Welcome to Personal Finance App! Start tracking your finances today.");
        notification.setType(NotificationType.SYSTEM);
        notification.setRead(false);

        log.info("-----------> Consumed UserCreatedEvent: {}", event);
        notificationRepository.save(notification);
    }
}
