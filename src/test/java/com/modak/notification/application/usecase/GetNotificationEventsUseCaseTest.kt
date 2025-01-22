
import com.modak.notification.application.usecase.GetNotificationEventsUseCase
import com.modak.notification.application.usecase.GetNotificationType
import com.modak.notification.exceptions.NotFoundException
import com.modak.notification.infraestructure.notificationEvent.NotificationEventRepository
import com.modak.notification.infraestructure.notificationEvent.entities.NotificationEvent
import com.modak.notification.models.NotificationEventModel
import com.modak.notification.models.NotificationTypeModel
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import java.time.LocalDateTime

class GetNotificationEventsUseCaseTest {

    private lateinit var getNotificationEventsUseCase: GetNotificationEventsUseCase
    private var notificationEventRepository: NotificationEventRepository = mockk()
    private var getNotificationType: GetNotificationType = mockk()

    @BeforeEach
    fun setUp() {
        getNotificationEventsUseCase = GetNotificationEventsUseCase(notificationEventRepository, getNotificationType)
    }

    @Test
    fun `apply should return NotificationEventModels when events are found`() {
        val notificationType = "Status"
        val usersId = 1L
        val sentAt = LocalDateTime.now()
        val notificationTypeModel = NotificationTypeModel(1L, notificationType, "Status notifications")
        val notificationEventEntity = NotificationEvent(1L, 1L, 1, sentAt)
        val notificationEventModel = NotificationEventModel(notificationTypeModel, sentAt)

        every { getNotificationType.getByName(notificationType) } returns Mono.just(notificationTypeModel)
        every { notificationEventRepository.findByNotificationTypeIdAndUsersId(notificationTypeModel.id, usersId) } returns Flux.just(notificationEventEntity)

        val result = getNotificationEventsUseCase.apply(notificationType, usersId)

        StepVerifier.create(result)
            .expectNext(notificationEventModel)
            .verifyComplete()

        verify { getNotificationType.getByName(notificationType) }
        verify { notificationEventRepository.findByNotificationTypeIdAndUsersId(notificationTypeModel.id, usersId) }
    }

    @Test
    fun `apply should return empty when no events are found`() {
        val notificationType = "Status"
        val usersId = 1L
        val notificationTypeModel = NotificationTypeModel(1L, notificationType, "Status notifications")

        every { getNotificationType.getByName(notificationType) } returns Mono.just(notificationTypeModel)
        every { notificationEventRepository.findByNotificationTypeIdAndUsersId(notificationTypeModel.id, usersId) } returns Flux.empty()

        val result = getNotificationEventsUseCase.apply(notificationType, usersId)

        StepVerifier.create(result)
            .verifyComplete()

        verify { getNotificationType.getByName(notificationType) }
        verify { notificationEventRepository.findByNotificationTypeIdAndUsersId(notificationTypeModel.id, usersId) }
    }
}