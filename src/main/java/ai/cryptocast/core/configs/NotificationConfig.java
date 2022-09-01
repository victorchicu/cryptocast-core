package ai.cryptocast.core.configs;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({StompProperties.class})
public class NotificationConfig {
    private StompProperties stompProperties;

    public StompProperties getStompProperties() {
        return stompProperties;
    }

    public void setStompProperties(StompProperties stompProperties) {
        this.stompProperties = stompProperties;
    }
}
