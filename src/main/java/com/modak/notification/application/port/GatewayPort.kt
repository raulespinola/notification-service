package com.modak.notification.application.port

import reactor.core.publisher.Mono

interface GatewayPort {
    fun send(userId: String, message: String?): Mono<Void>
}