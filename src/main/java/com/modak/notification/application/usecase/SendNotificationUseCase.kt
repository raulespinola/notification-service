package com.modak.notification.application.usecase

import com.modak.notification.infraestructure.gateway.GatewayAdapter
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class SendNotificationUseCase(
    private var gatewayAdapter: GatewayAdapter,
    private var allowSendNotificationUseCase: AllowSendNotificationUseCase,
    private var storeNotificationEventUseCase: StoreNotificationEventUseCase
) {

    /**
     * Send Notification if the limit was not reach
     * @param type, userId, message
     * @return void
     */
    fun apply(type: String, userId: Long, message: String): Mono<Boolean> {
        return allowSendNotificationUseCase.apply(type, userId)
            .flatMap {
                if (it) {
                    gatewayAdapter.send(userId, message)
                        .doOnSuccess {
                            log.info("Notification Send to UserId: {}, Type: {}", userId, type)
                            storeNotificationEventUseCase.apply(type, userId).subscribe()
                        }.then(Mono.just(true))
                } else {
                    log.info("Notification cannot send, the limit to send was reach")
                    Mono.just(false)
                }
            }
    }

    companion object {
        private val log: Logger = LoggerFactory.getLogger(SendNotificationUseCase::class.java)
    }

}