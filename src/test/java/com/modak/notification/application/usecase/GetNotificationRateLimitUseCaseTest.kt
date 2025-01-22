package com.modak.notification.application.usecase

import com.modak.notification.exceptions.NotFoundException
import com.modak.notification.infraestructure.notificationRateLimit.NotificationRateLimitRepository
import com.modak.notification.infraestructure.notificationRateLimit.entities.NotificationRateLimit
import com.modak.notification.models.NotificationRateLimitModel
import com.modak.notification.models.NotificationTypeModel
import com.modak.notification.models.PeriodTimeModel
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import java.time.LocalDateTime

class GetNotificationRateLimitUseCaseTest {

    private lateinit var getNotificationRateLimitUseCase: GetNotificationRateLimitUseCase
    private var notificationRateLimitRepository: NotificationRateLimitRepository= mockk()
    private var getNotificationType: GetNotificationType= mockk()
    private var getPeriodTimeUseCase: GetPeriodTimeUseCase= mockk()

    @BeforeEach
    fun setUp() {
        getNotificationRateLimitUseCase =
            GetNotificationRateLimitUseCase(notificationRateLimitRepository, getNotificationType, getPeriodTimeUseCase)
    }

    @Test
    fun `apply should return NotificationRateLimitModel when rate limit is found`() {
        val nameType = "News"
        val created = LocalDateTime.now()
        val notificationTypeModel = NotificationTypeModel(1L, nameType, "Email notifications")
        val rateLimitEntity = NotificationRateLimit(1L, 10, 5, 1L, created)
        val periodTimeModel = PeriodTimeModel("Daily", "Description", 60)
        val notificationRateLimitModel =
            NotificationRateLimitModel(notificationTypeModel, 5, periodTimeModel)

        every { getNotificationType.getByName(nameType) } returns Mono.just(notificationTypeModel)
        every { notificationRateLimitRepository.findByNotificationTypeId(notificationTypeModel.id) } returns Mono.just(
            rateLimitEntity
        )
        every { getPeriodTimeUseCase.apply(rateLimitEntity.periodTimeId) } returns Mono.just(periodTimeModel)

        val result = getNotificationRateLimitUseCase.apply(nameType)

        StepVerifier.create(result)
            .expectNext(notificationRateLimitModel)
            .verifyComplete()

        verify (exactly = 1) { getNotificationType.getByName(nameType) }
        verify (exactly = 1) { notificationRateLimitRepository.findByNotificationTypeId(notificationTypeModel.id) }
        verify (exactly = 1) { getPeriodTimeUseCase.apply(rateLimitEntity.periodTimeId) }
    }

    @Test
    fun `apply should return error when notification type is not found`() {
        val nameType = "Marketing"

        every { getNotificationType.getByName(nameType) } returns Mono.empty()

        val result = getNotificationRateLimitUseCase.apply(nameType)

        StepVerifier.create(result)
            .expectErrorMatches { it is NotFoundException && it.message == "Type Not Found" }
            .verify()

        verify (exactly = 1)  { getNotificationType.getByName(nameType) }
    }

    @Test
    fun `apply should return error when rate limit is not found`() {
        val nameType = "News"
        val notificationTypeModel = NotificationTypeModel(1L, nameType, "News notifications")

        every { getNotificationType.getByName(nameType) } returns Mono.just(notificationTypeModel)
        every { notificationRateLimitRepository.findByNotificationTypeId(notificationTypeModel.id) } returns Mono.empty()

        val result = getNotificationRateLimitUseCase.apply(nameType)

        StepVerifier.create(result)
            .expectErrorMatches { it is NotFoundException && it.message == "Rate Limit Not Found:${notificationTypeModel.id}" }
            .verify()

        verify (exactly = 1)  { getNotificationType.getByName(nameType) }
        verify (exactly = 1) { notificationRateLimitRepository.findByNotificationTypeId(notificationTypeModel.id) }
    }
}