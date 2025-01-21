package com.modak.notification.entities



data class PeriodTime (
    val id: Long? = null,
    val name: String,
    val description: String,
    val days: Int,
    val hours: Int,
    val minutes: Int,
    val seconds: Int
)