package com.crypto.core.notifications.configs;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "notification")
public class NotificationProperties {
    private Map<String, String> simpMappings;

    public Map<String, String> getSimpMappings() {
        return simpMappings;
    }

    public void setSimpMappings(Map<String, String> simpMappings) {
        this.simpMappings = simpMappings;
    }
}
