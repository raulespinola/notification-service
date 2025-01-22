package com.modak.notification.infraestructure.gateway

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono


@Component
class GatewayAdapter {
    fun send(userId: String, message: String?): Mono<Void> {
        println("sending message to user $userId")
        return Mono.empty()
    }
}