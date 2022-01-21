package com.trader.core.configs;

import com.trader.core.enums.NotificationType;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "notification")
public class NotificationProperties {
    private Map<NotificationType, String> destinations;

    public Map<NotificationType, String> getDestinations() {
        return destinations;
    }

    public void setDestinations(Map<NotificationType, String> destinations) {
        this.destinations = destinations;
    }
}
