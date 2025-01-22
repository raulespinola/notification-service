package com.modak.notification.application.usecase

import com.modak.notification.exceptions.NotFoundException
import com.modak.notification.infraestructure.periodTime.PeriodTimeRepository
import com.modak.notification.models.PeriodTimeModel
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class GetPeriodTimeUseCase(private var periodTimeRepository: PeriodTimeRepository) {

    fun apply(periodTimeId: Long): Mono<PeriodTimeModel> {
        return periodTimeRepository.findById(periodTimeId)
            .switchIfEmpty(Mono.error(NotFoundException("Period Time not Found$periodTimeId")))
            .mapNotNull { PeriodTimeModel(it.name, it.description, it.timeInMinutes) }
    }
}