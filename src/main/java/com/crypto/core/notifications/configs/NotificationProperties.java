package com.crypto.core.notifications.configs;

import com.crypto.core.notifications.enums.NotificationEvent;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "notification")
public class NotificationProperties {
    private Map<NotificationEvent, String> eventMappings;

    public Map<NotificationEvent, String> getEventMappings() {
        return eventMappings;
    }

    public void setEventMappings(Map<NotificationEvent, String> eventMappings) {
        this.eventMappings = eventMappings;
    }
}
