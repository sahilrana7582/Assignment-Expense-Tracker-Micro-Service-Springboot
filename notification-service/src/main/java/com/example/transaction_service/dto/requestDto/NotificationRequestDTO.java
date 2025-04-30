package com.example.transaction_service.dto.requestDto;


import com.example.transaction_service.enums.NotificationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRequestDTO {

    @NotBlank(message = "Notification ID cannot be blank")
    @Size(max = 100, message = "Notification ID must be less than 100 characters")
    private String notificationId;

    @NotBlank(message = "User ID is required")
    private String userId;

    @NotBlank(message = "Message is required")
    @Size(max = 255, message = "Message must be less than 255 characters")
    private String message;

    @NotNull(message = "Notification type is required")
    private NotificationType type;

    @NotNull(message = "Read status must be specified")
    private Boolean isRead;
}
