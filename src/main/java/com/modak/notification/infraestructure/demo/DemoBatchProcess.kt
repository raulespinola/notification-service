package com.modak.notification.infraestructure.demo

import com.modak.notification.application.usecase.SendNotificationUseCase
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.core.publisher.Mono.delay
import java.time.Duration

@Component
class DemoBatchProcess(private var sendNotificationUseCase: SendNotificationUseCase) {

    /**
     * Demo Process, this will execute from an endpoint, the results will show in the logs
     */
    fun apply(): Mono<Void> {

        log.info("DEMO MODAK NOTIFICATION STARTS")
        /**
         * News: not more than 1 per day for each recipient
         */
        return sendNotificationUseCase.apply("News", 1, "news 1")
            .flatMap {
                // This will fail
                sendNotificationUseCase.apply("News", 1, "news 2")
            }
            .flatMap {
                // This will fail
                sendNotificationUseCase.apply("News", 1, "news 2")
            }
            .flatMap {
                // This will not fail, is other user
                sendNotificationUseCase.apply("News", 2, "news 3")
            }
            .flatMap { // Previous User so will fail
                sendNotificationUseCase.apply("News", 2, "news 1")
            }
            .flatMap {
                /**
                 * Status: not more than 2 per minute for each recipient
                 */
                sendNotificationUseCase.apply("Status", 1, "update 1")
            }
            .flatMap {
                sendNotificationUseCase.apply("Status", 1, "update 2")
            }
            .flatMap {
                sendNotificationUseCase.apply("Status", 2, "update 2")
            }
            .flatMap {
                // This will fail, wait 1 minute
                sendNotificationUseCase.apply("Status", 1, "update 3")
            }
            .flatMap {
                // Wait for one minute
                log.info("Waiting for 1 minute")
                delay(Duration.ofMinutes(1))
                    .flatMap {   // Now will not fail
                        sendNotificationUseCase.apply("Status", 1, "final update")
                    }.then()
                    .doFinally { signalType ->
                        delay(Duration.ofSeconds(1))
                            .doOnSuccess {
                                log.info("DEMO MODAK NOTIFICATION ENDS")
                            }
                    }
            }

    }

    companion object {
        private val log: Logger = LoggerFactory.getLogger(SendNotificationUseCase::class.java)
    }

}