package com.modak.notification.infraestructure.periodTime

import com.modak.notification.infraestructure.periodTime.entities.PeriodTime
import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.stereotype.Repository

@Repository
interface PeriodTimeRepository: R2dbcRepository<PeriodTime, Long>