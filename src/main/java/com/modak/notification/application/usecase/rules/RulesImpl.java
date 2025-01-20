package com.modak.notification.application.usecase.rules;

import com.modak.notification.configuration.NotificationConfiguration;
import com.modak.notification.entities.NotificationRateLimit;
import com.modak.notification.exceptions.NotFoundTypeException;
import org.springframework.beans.factory.annotation.Autowired;

public class RulesImpl implements Rules{

    @Autowired
    private NotificationConfiguration notificationConfiguration;

    @Override
    public void apply(String notificationType, String message) {



    }

    public NotificationRateLimit getRatesByType(String notificationType){
       return notificationConfiguration.getUsers().stream()
               .filter(s-> notificationType.equals(s.getName()))
               .findFirst()
               .orElseThrow(() -> new NotFoundTypeException("Not Found Type:".concat(notificationType)));
    }
}
