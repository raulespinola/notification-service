package com.modak.notification.configuration;

import com.modak.notification.entities.NotificationRateLimit;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "notification-types")
@Getter
@Setter
public class NotificationConfiguration {

    private List<NotificationRateLimit> users;

}
