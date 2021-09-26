package com.crypto.core.notifications.configs;

import com.crypto.core.notifications.enums.NotificationEvent;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "notification")
public class NotificationProperties {
    private Map<NotificationEvent, String> simpMappings;

    public Map<NotificationEvent, String> getSimpMappings() {
        return simpMappings;
    }

    public void setSimpMappings(Map<NotificationEvent, String> simpMappings) {
        this.simpMappings = simpMappings;
    }
}
