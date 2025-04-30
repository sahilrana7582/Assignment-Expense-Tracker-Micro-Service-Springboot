package com.example.transaction_service.mapper;


import com.example.transaction_service.dto.requestDto.NotificationRequestDTO;
import com.example.transaction_service.dto.responseDto.NotificationResponseDTO;
import com.example.transaction_service.model.Notification;
import org.springframework.stereotype.Component;

@Component
public class NotificationMapper {

    public Notification toEntity(NotificationRequestDTO dto) {
        if (dto == null) return null;

        return Notification.builder()
                .notificationId(dto.getNotificationId())
                .userId(dto.getUserId())
                .message(dto.getMessage())
                .type(dto.getType())
                .isRead(dto.getIsRead())
                .build();
    }

    public NotificationResponseDTO toDto(Notification entity) {
        if (entity == null) return null;

        return NotificationResponseDTO.builder()
                .id(entity.getId())
                .notificationId(entity.getNotificationId())
                .userId(entity.getUserId())
                .message(entity.getMessage())
                .type(entity.getType())
                .isRead(entity.isRead())
                .build();
    }
}
