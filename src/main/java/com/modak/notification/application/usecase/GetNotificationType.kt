package com.modak.notification.application.usecase

import com.modak.notification.exceptions.NotFoundException
import com.modak.notification.infraestructure.notificationType.NotificationTypeRepository
import com.modak.notification.models.NotificationTypeModel
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class GetNotificationType(private var notificationTypeRepository: NotificationTypeRepository) {

    /**
     * Get NotificationTypeModel From a Name Type
     * @param name
     * @return NotificationTypeModel
     */
    fun getByName(name: String): Mono<NotificationTypeModel> {
        return notificationTypeRepository.findByName(name)
            .switchIfEmpty(Mono.error(NotFoundException("Type Not Found:$name")))
            .mapNotNull { it.id?.let { it1 -> NotificationTypeModel(it1, it.name, it.description) } }
            .doOnSuccess { log.info("Type Found: {}", it.name) }
    }

    /**
     * Get NotificationTypeModel From an Id
     * @param id
     * @return NotificationTypeModel
     */
    fun getById(id: Long): Mono<NotificationTypeModel> {
        return notificationTypeRepository.findById(id)
            .switchIfEmpty(Mono.error(NotFoundException("Type Not Found:$id")))
            .mapNotNull { it.id?.let { it1 -> NotificationTypeModel(it1, it.name, it.description) } }
            .doOnSuccess { log.info("Type Found: {}", it.name) }
    }

    companion object {
        private val log: Logger = LoggerFactory.getLogger(SendNotificationUseCase::class.java)
    }
}