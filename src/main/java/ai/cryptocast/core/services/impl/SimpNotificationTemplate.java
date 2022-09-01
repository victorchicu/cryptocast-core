package ai.cryptocast.core.services.impl;

import ai.cryptocast.core.domain.User;
import ai.cryptocast.core.enums.NotificationType;
import ai.cryptocast.core.configs.NotificationConfig;
import ai.cryptocast.core.services.NotificationTemplate;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SimpNotificationTemplate implements NotificationTemplate {
    private final NotificationConfig notificationConfig;
    private final SpelExpressionParser spelExpressionParser;
    private final SimpMessagingTemplate simpMessagingTemplate;

    public SimpNotificationTemplate(
            NotificationConfig notificationConfig,
            SpelExpressionParser spelExpressionParser,
            SimpMessagingTemplate simpMessagingTemplate
    ) {
        this.notificationConfig = notificationConfig;
        this.spelExpressionParser = spelExpressionParser;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @Override
    public <T> void sendNotification(User user, NotificationType type, T object) {
        //TODO: Send notification to a concrete user
        Optional.ofNullable(notificationConfig.getStompProperties().getDestinations().get(type))
                .ifPresent(destination -> {
                    Expression expression = spelExpressionParser.parseExpression(destination);
                    destination = expression.getValue(object, String.class);
                    simpMessagingTemplate.convertAndSend(destination, object);
                });
    }
}
