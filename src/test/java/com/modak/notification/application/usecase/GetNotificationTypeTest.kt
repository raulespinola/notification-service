package com.modak.notification.application.usecase

import com.modak.notification.exceptions.NotFoundException
import com.modak.notification.infraestructure.notificationType.NotificationTypeRepository
import com.modak.notification.infraestructure.notificationType.entities.NotificationType
import com.modak.notification.models.NotificationTypeModel
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import java.time.LocalDateTime


class GetNotificationTypeTest {

    private lateinit var getNotificationType: GetNotificationType
    private var notificationTypeRepository: NotificationTypeRepository = mockk()

    @BeforeEach
    fun setUp() {
        getNotificationType = GetNotificationType(notificationTypeRepository)
    }

    @Test
    fun `getByName should return NotificationTypeModel when type is found`() {
        val name = "EMAIL"
        val created = LocalDateTime.now()
        val notificationTypeEntity = NotificationType(1L, name, "Email notifications", created)
        val notificationTypeModel = NotificationTypeModel(1L, name, "Email notifications")

        every { notificationTypeRepository.findByName(name) } returns Mono.just(notificationTypeEntity)

        val result = getNotificationType.getByName(name)

        StepVerifier.create(result)
            .expectNext(notificationTypeModel)
            .verifyComplete()

        verify(exactly = 1) {
            notificationTypeRepository.findByName(name)
        }
    }

    @Test
    fun `getByName should return error when type is not found`() {
        val name = "EMAIL"

        every { notificationTypeRepository.findByName(name) } returns Mono.empty()

        val result = getNotificationType.getByName(name)

        StepVerifier.create(result)
            .expectErrorMatches { it is NotFoundException && it.message == "Type Not Found:$name" }
            .verify()

        verify(exactly = 1) { notificationTypeRepository.findByName(name) }
    }

    @Test
    fun `getById should return NotificationTypeModel when type is found`() {
        val id = 1L
        val created = LocalDateTime.now()
        val notificationTypeEntity = NotificationType(id, "EMAIL", "Email notifications", created)
        val notificationTypeModel = NotificationTypeModel(id, "EMAIL", "Email notifications")

        every { notificationTypeRepository.findById(id) } returns Mono.just(notificationTypeEntity)

        val result = getNotificationType.getById(id)

        StepVerifier.create(result)
            .expectNext(notificationTypeModel)
            .verifyComplete()

        verify(exactly = 1) { notificationTypeRepository.findById(id) }
    }

    @Test
    fun `getById should return error when type is not found`() {
        val id = 1L

        every { notificationTypeRepository.findById(id) } returns Mono.empty()

        val result = getNotificationType.getById(id)

        StepVerifier.create(result)
            .expectErrorMatches { it is NotFoundException && it.message == "Type Not Found:$id" }
            .verify()

        verify(exactly = 1) { notificationTypeRepository.findById(id) }
    }
}