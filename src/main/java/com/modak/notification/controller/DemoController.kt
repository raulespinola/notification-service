package com.modak.notification.controller

import com.modak.notification.infraestructure.demo.DemoBatchProcess
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono


@RestController
@RequestMapping("/notifications/v1/demo")
class DemoController(private var demoBatchProcess: DemoBatchProcess) {

    @GetMapping()
    fun demo():  Mono<Void>{
        return demoBatchProcess.apply().then()
    }
}