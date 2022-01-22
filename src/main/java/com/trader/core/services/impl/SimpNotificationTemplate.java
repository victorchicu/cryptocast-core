package com.trader.core.services.impl;

import com.trader.core.configs.NotificationProperties;
import com.trader.core.domain.User;
import com.trader.core.enums.NotificationType;
import com.trader.core.services.NotificationTemplate;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SimpNotificationTemplate implements NotificationTemplate {
    private final SpelExpressionParser spelExpressionParser;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final NotificationProperties notificationProperties;

    public SimpNotificationTemplate(
            SpelExpressionParser spelExpressionParser,
            SimpMessagingTemplate simpMessagingTemplate,
            NotificationProperties notificationProperties
    ) {
        this.spelExpressionParser = spelExpressionParser;
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.notificationProperties = notificationProperties;
    }

    @Override
    public <T> void sendNotification(User user, NotificationType type, T object) {
        //TODO: Send notification to a concrete user
        Optional.ofNullable(notificationProperties.getDestinations().get(type))
                .ifPresent(destination -> {
                    Expression expression = spelExpressionParser.parseExpression(destination);
                    destination = expression.getValue(object, String.class);
                    simpMessagingTemplate.convertAndSend(destination, object);
                });
    }
}
