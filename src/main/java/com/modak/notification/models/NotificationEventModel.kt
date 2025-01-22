package com.modak.notification.models

import java.time.LocalDateTime

data class NotificationEventModel (
    val type: NotificationTypeModel? = null,
    val sentAt: LocalDateTime
)