package com.modak.notification.infraestructure.notificationEvent

import com.modak.notification.infraestructure.notificationEvent.entities.NotificationEvent
import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux


@Repository
interface NotificationEventRepository: R2dbcRepository<NotificationEvent, Long> {
    fun findByNotificationTypeId(notificationTypeId: Long): Flux<NotificationEvent>
}