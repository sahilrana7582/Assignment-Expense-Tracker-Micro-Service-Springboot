package com.example.notification_service.service;


import com.example.notification_service.dto.requestDto.NotificationRequestDTO;
import com.example.notification_service.dto.requestDto.NotificationUpdateReadStatusDTO;
import com.example.notification_service.dto.responseDto.NotificationResponseDTO;
import com.example.notification_service.exception.ResourceNotFound;
import com.example.notification_service.mapper.NotificationMapper;
import com.example.notification_service.model.Notification;
import com.example.notification_service.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
}
