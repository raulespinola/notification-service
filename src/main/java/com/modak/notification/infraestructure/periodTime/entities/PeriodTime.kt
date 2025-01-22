package com.modak.notification.infraestructure.periodTime.entities

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime


@Table("period_time")
data class PeriodTime (
    @Id val id: Long? = null,
    val name: String,
    val description: String,
    val timeInMinutes: Int,
)