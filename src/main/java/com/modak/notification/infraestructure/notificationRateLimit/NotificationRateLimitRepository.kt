package com.modak.notification.infraestructure.notificationRateLimit

import com.modak.notification.infraestructure.notificationRateLimit.entities.NotificationRateLimit
import com.modak.notification.infraestructure.notificationType.entities.NotificationType
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
interface NotificationRateLimitRepository: R2dbcRepository<NotificationRateLimit, Long> {
    fun findByNotificationTypeId(id: Long): Mono<NotificationRateLimit>
}



