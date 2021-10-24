package com.crypto.core.notifications.services.impl;

import com.crypto.core.notifications.configs.NotificationProperties;
import com.crypto.core.notifications.enums.NotificationType;
import com.crypto.core.notifications.services.NotificationTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionService;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;

@Service
public class SimpNotificationTemplate implements NotificationTemplate {
    private static final Logger LOGGER = LoggerFactory.getLogger(SimpNotificationTemplate.class);

    private final ConversionService conversionService;
    private final SpelExpressionParser spelExpressionParser;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final NotificationProperties notificationProperties;

    public SimpNotificationTemplate(
            ConversionService conversionService,
            SpelExpressionParser spelExpressionParser,
            SimpMessagingTemplate simpMessagingTemplate,
            NotificationProperties notificationProperties
    ) {
        this.conversionService = conversionService;
        this.spelExpressionParser = spelExpressionParser;
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.notificationProperties = notificationProperties;
    }

    @Override
    public <T> void sendNotification(Principal principal, NotificationType type, T object) {
        Optional.ofNullable(notificationProperties.getDestinations().get(type))
                .ifPresent(destination -> {
                    Expression expression = spelExpressionParser.parseExpression(destination);
                    destination = expression.getValue(object, String.class);
                    simpMessagingTemplate.convertAndSend(destination, object);
                });
    }
}
