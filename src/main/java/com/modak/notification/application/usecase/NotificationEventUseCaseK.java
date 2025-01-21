package com.modak.notification.application.usecase;

import com.modak.notification.entities.NotificationType;
import com.modak.notification.infraestructure.notificationEvent.NotificationEventRepository;
import com.modak.notification.infraestructure.notificationEvent.mapper.NotificationMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NotificationEventUseCaseK {

    private NotificationEventRepository notificationEventRepository;
    private NotificationMapper notificationMapper;

    public void apply(NotificationType notificationType){
       notificationEventRepository.save(notificationMapper.toEntity(notificationType));
    }

}
