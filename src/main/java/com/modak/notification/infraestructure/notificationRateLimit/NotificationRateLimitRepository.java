package com.modak.notification.infraestructure.notificationRateLimit;

import com.modak.notification.entities.NotificationRateLimit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRateLimitRepository extends JpaRepository<NotificationRateLimit, Long> {
}
