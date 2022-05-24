package com.coinbank.core.domain.configs;

import com.coinbank.core.enums.NotificationType;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties(prefix = "stomp")
public class StompProperties {
    private Map<NotificationType, String> destinations;

    public Map<NotificationType, String> getDestinations() {
        return destinations;
    }

    public void setDestinations(Map<NotificationType, String> destinations) {
        this.destinations = destinations;
    }
}
