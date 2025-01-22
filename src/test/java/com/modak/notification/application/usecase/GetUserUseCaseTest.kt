package com.modak.notification.application.usecase

import com.modak.notification.exceptions.NotFoundException
import com.modak.notification.infraestructure.user.entities.UserRepository
import com.modak.notification.infraestructure.user.entities.Users
import com.modak.notification.models.UserModel
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

class GetUserUseCaseTest {

    private lateinit var getUserUseCase: GetUserUseCase
    private var userRepository: UserRepository = mockk()

    @BeforeEach
    fun setUp() {
        getUserUseCase = GetUserUseCase(userRepository)
    }

    @Test
    fun `apply should return UserModel when user is found`() {
        val userId = 1L
        val userEntity = Users(userId, "John Doe")
        val userModel = UserModel(userId, "John Doe")

        every { userRepository.findById(userId) } returns
                Mono.just(userEntity)

        val result = getUserUseCase.apply(userId)

        StepVerifier.create(result)
            .expectNext(userModel)
            .verifyComplete()
    }

    @Test
    fun `apply should return error when user is not found`() {
        val userId = 1L

        every {
            userRepository.findById(userId)
        } returns Mono.empty()

        val result = getUserUseCase.apply(userId)

        StepVerifier.create(result)
            .expectErrorMatches { it is NotFoundException && it.message == "User Not Found: $userId" }
            .verify()
    }
}