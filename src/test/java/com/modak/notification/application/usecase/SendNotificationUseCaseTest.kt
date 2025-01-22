package com.modak.notification.application.usecase

import com.modak.notification.infraestructure.gateway.GatewayAdapter
import com.modak.notification.models.NotificationEventModel
import com.modak.notification.models.NotificationTypeModel
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import java.time.LocalDateTime

class SendNotificationUseCaseTest{

    private lateinit var sendNotificationUseCase: SendNotificationUseCase

    private var allowSendNotificationUseCase: AllowSendNotificationUseCase = mockk()
    private var gatewayAdapter: GatewayAdapter = mockk()
    private var storeNotificationEventUseCase: StoreNotificationEventUseCase = mockk()

    @BeforeEach
    fun setUp() {
        sendNotificationUseCase = SendNotificationUseCase(gatewayAdapter, allowSendNotificationUseCase, storeNotificationEventUseCase)
    }

    @Test
    fun `apply should return true when notification is sent`() {
        val type = "Status"
        val userId = 1L
        val message = "Test Message"
        val notificationEventModel = NotificationEventModel(NotificationTypeModel(1, type,"desc"), LocalDateTime.now())


        every{
            allowSendNotificationUseCase.apply(type, userId)
        } returns(Mono.just(true))
        every{
            gatewayAdapter.send(userId, message)
        } returns Mono.just(Unit)
        every{
            storeNotificationEventUseCase.apply(type, userId)
        } returns Mono.just(notificationEventModel)

        val result = sendNotificationUseCase.apply(type, userId, message)

        StepVerifier.create(result)
            .expectNext(true)
            .verifyComplete()
    }

    @Test
    fun `apply should return false when notification cannot be sent`() {
        val type = "News"
        val userId = 1L
        val message = "Test Message"

        every {
            allowSendNotificationUseCase.apply(type, userId)
        } returns(Mono.just(false))

        val result = sendNotificationUseCase.apply(type, userId, message)

        StepVerifier.create(result)
            .expectNext(false)
            .verifyComplete()
    }

}