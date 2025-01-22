package com.modak.notification.application.usecase

import com.modak.notification.exceptions.NotFoundException
import com.modak.notification.infraestructure.user.entities.UserRepository
import com.modak.notification.models.UserModel
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class GetUserUseCase(private var userRepository: UserRepository) {
    fun apply(userId:Long): Mono<UserModel> {
        return userRepository.findById(userId)
            .switchIfEmpty(Mono.error(NotFoundException("User Not Found: {}$userId")))
            .mapNotNull { it.id?.let { it1 -> UserModel(it1, it.name) } }
    }
}