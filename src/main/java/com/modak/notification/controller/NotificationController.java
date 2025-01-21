package com.modak.notification.controller;

import com.example.demo.service.NotificationService;
import com.modak.notification.application.usecase.NotificationUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationUseCase notificationUseCase;

    @PostMapping("/send")
    public String sendNotification(@RequestParam String username, @RequestParam String message) {
        //notificationUseCase.send(username, message);
        return "Notification sent";
    }
}