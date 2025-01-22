package com.modak.notification.controller

import com.modak.notification.application.usecase.GetNotificationEventsUseCase
import com.modak.notification.application.usecase.GetNotificationRateLimitUseCase
import com.modak.notification.application.usecase.SendNotificationUseCase
import com.modak.notification.models.NotificationRateLimitModel
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono


@RestController
@RequestMapping("/notifications/v1")
class NotificationController(
    private var sendNotificationUseCase: SendNotificationUseCase,
    private var getNotificationRateLimitUseCase: GetNotificationRateLimitUseCase,
    private var getNotificationEventsUseCase: GetNotificationEventsUseCase
    ) {

    @PostMapping("/send")
    fun sendNotification(@RequestParam type: String, @RequestParam userId: Long, @RequestParam message: String): Mono<ResponseEntity<Unit>> {
        return sendNotificationUseCase.apply(type, userId, message)
            .map {
                if (it) {
                    ResponseEntity.ok().body(Unit)
                } else {
                    ResponseEntity.badRequest().body(Unit)
                }
            }
    }

    @GetMapping("/rate-limit")
    fun getNotificationRateLimit(@RequestParam type: String): Mono<ResponseEntity<NotificationRateLimitModel>> {
        return getNotificationRateLimitUseCase.apply(type)
            .mapNotNull { ResponseEntity.ok(it) }
    }

}