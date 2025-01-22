package com.modak.notification.application.usecase

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.time.LocalDateTime

@Service
class AllowSendNotificationUseCase(
    private var getNotificationRateLimitUseCase: GetNotificationRateLimitUseCase,
    private var getNotificationEventsUseCase: GetNotificationEventsUseCase,
    private var getUserUseCase: GetUserUseCase
) {

    /**
     * Check if the rate limit to send a new notification was reach for the last minutes
     * corresponding with the notification type
     * @param type
     * @return Boolean
     */
    fun apply(type: String, userId:Long): Mono<Boolean> {
        return getUserUseCase.apply(userId)
            .flatMap { user-> getNotificationRateLimitUseCase.apply(type)
                .flatMap { rateLimit ->
                    getNotificationEventsUseCase
                        .apply(type, userId)
                        .filter { event ->
                            event.sentAt.isAfter(
                                LocalDateTime.now()
                                    .minusMinutes(rateLimit.periodTime.timeInMinutes.toLong())
                            )
                        }
                        .count()
                        .map {
                            log.info(
                                "Total Events: {} for user: {} in the last: {} min, limit to Send: {}",
                                it, user.name, rateLimit.periodTime.timeInMinutes, rateLimit.limitToSend
                            )
                            it < rateLimit.limitToSend
                        }
                }}


    }

    companion object {
        private val log: Logger = LoggerFactory.getLogger(SendNotificationUseCase::class.java)
    }

}