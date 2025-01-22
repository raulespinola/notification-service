package com.modak.notification.infraestructure.notificationEvent.mapper

import com.modak.notification.infraestructure.notificationEvent.entities.NotificationEvent
import com.modak.notification.models.NotificationTypeModel
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class NotificationMapper {
    fun toEntity(notificationType: NotificationTypeModel): NotificationEvent {
        return NotificationEvent(null, notificationType.id, LocalDateTime.now())
    }

}