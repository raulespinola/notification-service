package com.modak.notification.infraestructure.gateway

import com.modak.notification.application.usecase.SendNotificationUseCase
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono


@Component
class GatewayAdapter {
    fun send(userId: Long, message: String?): Mono<Unit> {
        return Mono.just(Unit)
            .doOnSuccess { log.info("sending message to user $userId") }
    }

    companion object{
        private val log: Logger = LoggerFactory.getLogger(SendNotificationUseCase::class.java)
    }
}