package com.modak.notification.infraestructure.notificationType

import com.modak.notification.infraestructure.notificationType.entities.NotificationType
import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
interface NotificationTypeRepository: R2dbcRepository<NotificationType, Long> {
    fun findByName(name:String): Mono<NotificationType>
}