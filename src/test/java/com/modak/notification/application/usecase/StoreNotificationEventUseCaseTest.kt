package com.modak.notification.application.usecase

import com.modak.notification.infraestructure.notificationEvent.NotificationEventRepository
import com.modak.notification.infraestructure.notificationEvent.entities.NotificationEvent
import com.modak.notification.infraestructure.notificationEvent.mapper.NotificationMapper
import com.modak.notification.models.NotificationEventModel
import com.modak.notification.models.NotificationTypeModel
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import java.time.LocalDateTime


class StoreNotificationEventUseCaseTest {

    private lateinit var storeNotificationEventUseCase: StoreNotificationEventUseCase
    private var getNotificationType: GetNotificationType = mockk()
    private var notificationEventRepository: NotificationEventRepository = mockk()
    private var notificationMapper: NotificationMapper = mockk()

    @BeforeEach
    fun setUp() {
        storeNotificationEventUseCase= StoreNotificationEventUseCase(notificationEventRepository, notificationMapper, getNotificationType)
    }

    @Test
    fun `apply should store new event and return NotificationEventModel`() {
        val type = "Status"
        val userId = 1L
        val dateTime = LocalDateTime.now()
        val notificationEventEntity = NotificationEvent(1L, 1, 1, dateTime )
        val notificationTypeModel = NotificationTypeModel(1, type,"desc")
        val notificationEventModel = NotificationEventModel(NotificationTypeModel(1, type,"desc"), dateTime)

        every{
            getNotificationType.getByName(type)
        } returns Mono.just(notificationTypeModel)

        every{
            notificationMapper.toEntity(notificationTypeModel, userId)
        } returns notificationEventEntity

        every {
            notificationEventRepository.save(notificationEventEntity)
        } returns Mono.just(notificationEventEntity)

        val result = storeNotificationEventUseCase.apply(type, userId)

        StepVerifier.create(result)
            .expectNext(notificationEventModel)
            .verifyComplete()
    }
}