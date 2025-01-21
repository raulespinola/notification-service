package com.modak.notification.infraestructure.notificationEvent.mapper;

import com.modak.notification.entities.NotificationType;

import java.time.LocalDateTime;

public class NotificationMapper {

    public NotificationEvent toEntity(NotificationType notificationType){
        return new NotificationEvent(null, notificationType, LocalDateTime.now());
    }

}
