package com.modak.notification.application.usecase

import com.modak.notification.exceptions.NotFoundException
import com.modak.notification.infraestructure.notificationRateLimit.NotificationRateLimitRepository
import com.modak.notification.models.NotificationRateLimitModel
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono


@Service
class GetNotificationRateLimitUseCase(
    private var notificationRateLimitRepository: NotificationRateLimitRepository,
    private var getNotificationType: GetNotificationType,
    private var getPeriodTimeUseCase: GetPeriodTimeUseCase
) {


    /**
     * Get the Rate Limit for a Notification Type
     * @param nameType
     * @return Mono NotificationRateLimitModel
     */
    fun apply(nameType: String): Mono<NotificationRateLimitModel> {
        return getNotificationType.getByName(nameType)
            .flatMap { notificationType ->
                log.info("Notification Type Found: {}", notificationType.name)
                notificationRateLimitRepository.findByNotificationTypeId(notificationType.id)
                    .switchIfEmpty(Mono.error(NotFoundException("Rate Limit Not Found:{}${notificationType.id}")))
                    .flatMap { rateLimit ->
                        getPeriodTimeUseCase.apply(rateLimit.periodTimeId)
                            .mapNotNull {
                                NotificationRateLimitModel(
                                    notificationType,
                                    rateLimit.limitToSend, it
                                )
                            }
                    }
            }
            .switchIfEmpty(Mono.error(NotFoundException("Type Not Found")))
            .doOnSuccess { log.info("Type Found: {}", it) }

    }

    companion object {
        private val log: Logger = LoggerFactory.getLogger(SendNotificationUseCase::class.java)
    }

}