package com.modak.notification.application.usecase


import com.modak.notification.exceptions.NotFoundException
import com.modak.notification.models.*
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import java.time.LocalDateTime

class AllowSendNotificationUseCaseTest {

    private lateinit var allowSendNotificationUseCase: AllowSendNotificationUseCase
    private var getNotificationRateLimitUseCase: GetNotificationRateLimitUseCase  = mockk()
    private var getNotificationEventsUseCase: GetNotificationEventsUseCase  = mockk()
    private var getUserUseCase: GetUserUseCase = mockk()

    @BeforeEach
    fun setUp() {
        allowSendNotificationUseCase = AllowSendNotificationUseCase(
            getNotificationRateLimitUseCase,
            getNotificationEventsUseCase,
            getUserUseCase
        )
    }

    @Test
    fun `apply should return true when user can send notification`() {
        val type = "EMAIL"
        val userId = 1L
        val user = UserModel(userId, "John Doe")
        val rateLimit = NotificationRateLimitModel(
            NotificationTypeModel(1L, type, "Email notifications"),
            5,
            PeriodTimeModel("Period Name", "Description", 10)
        )
        val events = listOf(
            NotificationEventModel(rateLimit.type, LocalDateTime.now().minusMinutes(5))
        )

        every { getUserUseCase.apply(userId) } returns Mono.just(user)
        every { getNotificationRateLimitUseCase.apply(type) } returns Mono.just(rateLimit)
        every { getNotificationEventsUseCase.apply(type, userId) } returns Flux.fromIterable(events)

        val result = allowSendNotificationUseCase.apply(type, userId)

        StepVerifier.create(result)
            .expectNext(true)
            .verifyComplete()

        verify { getUserUseCase.apply(userId) }
        verify { getNotificationRateLimitUseCase.apply(type) }
        verify { getNotificationEventsUseCase.apply(type, userId) }
    }

    @Test
    fun `apply should return false when user cannot send notification`() {
        val type = "News"
        val userId = 1L
        val user = UserModel(userId, "Raul")
        val rateLimit = NotificationRateLimitModel(
            NotificationTypeModel(1L, type, "News notifications"),
            5,
            PeriodTimeModel("Period Name", "Description", 10)
        )
        val events = listOf(
            NotificationEventModel(rateLimit.type, LocalDateTime.now().minusMinutes(2)),
            NotificationEventModel(rateLimit.type, LocalDateTime.now().minusMinutes(5)),
            NotificationEventModel(rateLimit.type, LocalDateTime.now().minusMinutes(7)),
            NotificationEventModel(rateLimit.type, LocalDateTime.now().minusMinutes(8)),
            NotificationEventModel(rateLimit.type, LocalDateTime.now().minusMinutes(9))
        )

        every { getUserUseCase.apply(userId) } returns Mono.just(user)
        every { getNotificationRateLimitUseCase.apply(type) } returns Mono.just(rateLimit)
        every { getNotificationEventsUseCase.apply(type, userId) } returns Flux.fromIterable(events)

        val result = allowSendNotificationUseCase.apply(type, userId)

        StepVerifier.create(result)
            .expectNext(false)
            .verifyComplete()

        verify { getUserUseCase.apply(userId) }
        verify { getNotificationRateLimitUseCase.apply(type) }
        verify { getNotificationEventsUseCase.apply(type, userId) }
    }

}