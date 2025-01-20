package com.modak.notification.infraestructure.notification;

import com.modak.notification.entities.NotificationEvent;
import com.modak.notification.entities.NotificationType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<NotificationType, Long> {
}
