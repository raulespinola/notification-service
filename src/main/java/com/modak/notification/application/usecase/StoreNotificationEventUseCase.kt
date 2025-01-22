package com.modak.notification.application.usecase

import com.modak.notification.infraestructure.notificationEvent.NotificationEventRepository
import com.modak.notification.infraestructure.notificationEvent.mapper.NotificationMapper
import com.modak.notification.models.NotificationEventModel
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono


@Service
class StoreNotificationEventUseCase(
    private var notificationEventRepository: NotificationEventRepository,
    private var notificationMapper: NotificationMapper,
    private var getNotificationType: GetNotificationType
) {
    /**
     * Store the new Notification event at the current time
     * @param notificationType
     * @return notificationEventModel
     */
    fun apply(type: String, userId:Long): Mono<NotificationEventModel> {
        return getNotificationType.getByName(type)
            .flatMap { notificationType ->
                log.info("Notification Type Found: {}", notificationType.name)
                notificationEventRepository.save(notificationMapper.toEntity(notificationType, userId))
                    .doOnSuccess { log.info("Store New Event: {}", it) }
                    .map { NotificationEventModel(notificationType, it.sentAt) }
            }
    }

    companion object {
        private val log: Logger = LoggerFactory.getLogger(SendNotificationUseCase::class.java)
    }
}