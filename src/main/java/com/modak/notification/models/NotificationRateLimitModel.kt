package com.modak.notification.models



data class NotificationRateLimitModel (
    var type: NotificationTypeModel,
    var limitToSend: Int,
    var periodTime: PeriodTimeModel,
)