package ai.cryptocast.core.web.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    private static final String[] ENDPOINT_PATHS = {"/websocket"};
    private static final String[] APPLICATION_DESTINATION_PREFIXES = {"/app"};
    private static final String[] STOMP_BROKER_DESTINATION_PREFIXES = {"/topic"};

    @Value("${rabbitmq.relayHost}")
    private String relayHost;
    @Value("${rabbitmq.relayPort}")
    private Integer relayPort;

    private final RabbitProperties rabbitProperties;

    public WebSocketConfig(RabbitProperties rabbitProperties) {
        this.rabbitProperties = rabbitProperties;
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.setApplicationDestinationPrefixes(APPLICATION_DESTINATION_PREFIXES)
                .enableStompBrokerRelay(STOMP_BROKER_DESTINATION_PREFIXES)
                .setRelayHost(relayHost)
                .setRelayPort(relayPort)
                .setClientLogin(rabbitProperties.getUsername())
                .setClientPasscode(rabbitProperties.getPassword());
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint(ENDPOINT_PATHS)
                .setAllowedOriginPatterns("http://localhost:4200")
                .withSockJS();
    }
}
