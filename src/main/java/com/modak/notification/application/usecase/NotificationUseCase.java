package com.modak.notification.application.usecase;

import com.modak.notification.infraestructure.gateway.Gateway;
import com.modak.notification.infraestructure.notificationRateLimit.NotificationRateLimitRepository;
import com.modak.notification.infraestructure.periodTime.PeriodTimeRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NotificationUseCase {

    private Gateway gateway;

    private NotificationEventUseCaseK notificationEventUseCaseK;
    private PeriodTimeRepository periodTimeRepository;
    private NotificationRateLimitRepository notificationRateLimitRepository;



    public void send(String type, String userId, String message) {

    }



}