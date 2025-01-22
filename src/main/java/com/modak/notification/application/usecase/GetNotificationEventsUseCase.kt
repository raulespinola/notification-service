package com.modak.notification.application.usecase

import com.modak.notification.infraestructure.notificationEvent.NotificationEventRepository
import com.modak.notification.models.NotificationEventModel
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class GetNotificationEventsUseCase(
    private var notificationEventRepository: NotificationEventRepository,
    private var getNotificationType: GetNotificationType) {


    /**
     * Return the Notification Events from a Notification Type
     * @param notificationType
     * @return Flux from NotificationEventModel
     */
    fun apply(notificationType:String): Flux<NotificationEventModel> {
        return getNotificationType.getByName(notificationType)
            .flatMapMany{ notificationType ->
                notificationEventRepository.findByNotificationTypeId(notificationType.id)
                    .mapNotNull { it.sentAt?.let { it1 -> NotificationEventModel(notificationType, it1) } }
            }
    }

    companion object{
        private val log: Logger = LoggerFactory.getLogger(SendNotificationUseCase::class.java)
    }
}