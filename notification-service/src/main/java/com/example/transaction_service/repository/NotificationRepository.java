package com.example.transaction_service.repository;

import com.example.transaction_service.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    Optional<Notification> findByNotificationId(String notificationId);

    List<Notification> findAllByUserId(String userId);

    List<Notification> findAllByUserIdAndIsReadFalse(String userId);

}
