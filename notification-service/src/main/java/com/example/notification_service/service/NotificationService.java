package com.example.notification_service.service;



import com.example.notification_service.dto.requestDto.*;
import com.example.notification_service.dto.responseDto.*;

import java.util.List;

public interface NotificationService {

    NotificationResponseDTO createNotification(NotificationRequestDTO requestDTO);

    List<NotificationResponseDTO> getAllNotificationsByUser(String userId);

    NotificationResponseDTO updateReadStatus(NotificationUpdateReadStatusDTO statusDTO);

    void deleteNotification(String notificationId);
}
