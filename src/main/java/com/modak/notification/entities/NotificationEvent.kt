package com.modak.notification.entities

import java.time.LocalDateTime

data class NotificationEvent (
    val id: Long? = null,
    val type: NotificationType? = null,
    val sentAt: LocalDateTime? = null
)
