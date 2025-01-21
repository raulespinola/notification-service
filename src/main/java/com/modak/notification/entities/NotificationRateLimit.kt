package com.modak.notification.entities



data class NotificationRateLimit (
    var id: Long? = null,
    var type: NotificationType,
    var limit: Int,
    var periodTime: PeriodTime,
)