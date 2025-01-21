package com.modak.notification.infraestructure.periodTime

import com.modak.notification.entities.PeriodTime
import org.springframework.stereotype.Repository

@Repository
interface PeriodTimeRepository : ReactiveCrudRepository<PeriodTime, Long>