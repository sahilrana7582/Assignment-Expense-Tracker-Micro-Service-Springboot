package com.example.user_service.dto.requestDto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationUpdateReadStatusDTO {

    @NotBlank(message = "Notification ID is required")
    private String notificationId;

    @NotNull(message = "Read status must be provided")
    private Boolean isRead;
}
