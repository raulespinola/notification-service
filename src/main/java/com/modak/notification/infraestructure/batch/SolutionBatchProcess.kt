package com.modak.notification.infraestructure.batch

import com.modak.notification.application.usecase.SendNotificationUseCase
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class SolutionBatchProcess (private var sendNotificationUseCase: SendNotificationUseCase) {
    fun apply(): Mono<Void> {


        sendNotificationUseCase.apply("news", "user", "news 1");
/*
        service.send("news", "user", "news 2");

        service.send("news", "user", "news 3");

        service.send("news", "another user", "news 1");

        service.send("update", "user", "update 1");

 */
return Mono.empty()

    }

}