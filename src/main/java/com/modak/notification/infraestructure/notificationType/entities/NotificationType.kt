package com.modak.notification.infraestructure.notificationType.entities

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime


@Table("notification_type")
data class NotificationType(
    @Id val id: Long? = null,
    val name: String,
    val description: String,
    val createdAt: LocalDateTime
)
