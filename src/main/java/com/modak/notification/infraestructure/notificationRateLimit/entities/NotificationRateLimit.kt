package com.modak.notification.infraestructure.notificationRateLimit.entities

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime


@Table("notification_rate_limit")
data class NotificationRateLimit (
    @Id var id: Long? = null,
    var notificationTypeId: Long,
    var limitToSend: Int,
    var periodTimeId: Long,
    val createdAt: LocalDateTime
)