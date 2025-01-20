package com.modak.notification.application.usecase.notification;

import com.modak.notification.configuration.NotificationConfiguration;
import com.modak.notification.infraestructure.gateway.Gateway;
import org.springframework.beans.factory.annotation.Autowired;

public class NotificationServiceImpl implements NotificationService {

    private Gateway gateway;

    @Autowired
    private NotificationConfiguration notificationConfiguration;

    public NotificationServiceImpl(Gateway gateway) {

        this.gateway = gateway;

    }


    @Override
    public void send(String type, String userId, String message) {


    }



}