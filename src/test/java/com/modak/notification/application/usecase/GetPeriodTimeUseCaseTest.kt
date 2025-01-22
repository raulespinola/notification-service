package com.modak.notification.application.usecase

import com.modak.notification.exceptions.NotFoundException
import com.modak.notification.infraestructure.periodTime.PeriodTimeRepository
import com.modak.notification.infraestructure.periodTime.entities.PeriodTime
import com.modak.notification.models.PeriodTimeModel
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

class GetPeriodTimeUseCaseTest {

    private lateinit var getPeriodTimeUseCase: GetPeriodTimeUseCase
    private var periodTimeRepository: PeriodTimeRepository = mockk()

    @BeforeEach
    fun setUp() {
        getPeriodTimeUseCase = GetPeriodTimeUseCase(periodTimeRepository)
    }

    @Test
    fun `apply should return PeriodTimeModel when periodTime is found`() {
        val periodTimeId = 1L
        val periodTimeEntity = PeriodTime(periodTimeId, "Daily", "Description", 60)
        val periodTimeModel = PeriodTimeModel("Daily", "Description", 60)

        every {
            periodTimeRepository.findById(periodTimeId)
        } returns Mono.just(periodTimeEntity)

        val result = getPeriodTimeUseCase.apply(periodTimeId)

        StepVerifier.create(result)
            .expectNext(periodTimeModel)
            .verifyComplete()
    }

    @Test
    fun `apply should return error when periodTime is not found`() {
        val periodTimeId = 1L

        every {
            periodTimeRepository.findById(periodTimeId)
        } returns Mono.empty()

        val result = getPeriodTimeUseCase.apply(periodTimeId)

        StepVerifier.create(result)
            .expectErrorMatches { it is NotFoundException && it.message == "Period Time not Found$periodTimeId" }
            .verify()
    }
}