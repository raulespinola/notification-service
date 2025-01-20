package com.modak.notification.application.usecase.rules;

interface Rules {
    void apply(String notificationType, String message);
}
