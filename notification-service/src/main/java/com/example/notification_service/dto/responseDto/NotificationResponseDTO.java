package com.example.notification_service.dto.responseDto;


import com.example.notification_service.enums.NotificationType;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationResponseDTO {

    private Long id;
    private String notificationId;
    private String userId;
    private String message;
    private NotificationType type;
    private boolean isRead;

}
