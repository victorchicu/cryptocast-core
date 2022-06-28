package com.coinbank.core.services.impl;

import com.coinbank.core.configs.NotificationConfig;
import com.coinbank.core.domain.User;
import com.coinbank.core.enums.NotificationType;
import com.coinbank.core.services.NotificationTemplate;
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
