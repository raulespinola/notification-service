package com.modak.notification.application.usecase.notification;

interface NotificationService {
    void send(String type, String userId, String message);
}

