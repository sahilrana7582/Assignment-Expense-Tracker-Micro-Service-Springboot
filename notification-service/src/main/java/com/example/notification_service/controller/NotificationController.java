package com.example.notification_service.controller;


import com.example.notification_service.dto.requestDto.NotificationRequestDTO;
import com.example.notification_service.dto.requestDto.NotificationUpdateReadStatusDTO;
import com.example.notification_service.dto.responseDto.NotificationResponseDTO;
import com.example.notification_service.service.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    // Create a new notification
    @PostMapping
    public ResponseEntity<NotificationResponseDTO> createNotification(
            @Valid @RequestBody NotificationRequestDTO requestDTO) {
        NotificationResponseDTO response = notificationService.createNotification(requestDTO);
        return ResponseEntity.status(201).body(response);
    }

    // Get all notifications for a user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<NotificationResponseDTO>> getNotificationsByUserId(
            @PathVariable String userId) {
        List<NotificationResponseDTO> notifications = notificationService.getAllNotificationsByUser(userId);
        return ResponseEntity.ok(notifications);
    }

    // Update read status of a notification
    @PatchMapping("/read-status")
    public ResponseEntity<NotificationResponseDTO> updateReadStatus(
            @Valid @RequestBody NotificationUpdateReadStatusDTO statusDTO) {
        NotificationResponseDTO updated = notificationService.updateReadStatus(statusDTO);
        return ResponseEntity.ok(updated);
    }

    // Delete a notification
    @DeleteMapping("/{notificationId}")
    public ResponseEntity<Void> deleteNotification(@PathVariable String notificationId) {
        notificationService.deleteNotification(notificationId);
        return ResponseEntity.noContent().build();
    }
}

