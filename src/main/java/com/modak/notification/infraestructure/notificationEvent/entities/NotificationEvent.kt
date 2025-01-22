package com.modak.notification.infraestructure.notificationEvent.entities

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime


@Table("notification_event")
data class NotificationEvent (
    @Id val id: Long?=0,
    val notificationTypeId: Long,
    val sentAt: LocalDateTime
)
