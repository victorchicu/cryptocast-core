package com.crypto.core.notifications.services.impl;

import com.crypto.core.notifications.configs.NotificationProperties;
import com.crypto.core.notifications.services.NotificationEmitter;
import org.springframework.core.convert.ConversionService;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class SimpNotificationEmitter implements NotificationEmitter {
    private final ConversionService conversionService;
    private final NotificationProperties notificationProperties;
    private final SimpMessageSendingOperations simpMessageSendingOperations;

    public SimpNotificationEmitter(
            ConversionService conversionService,
            NotificationProperties notificationProperties,
            SimpMessageSendingOperations simpMessageSendingOperations
    ) {
        this.conversionService = conversionService;
        this.notificationProperties = notificationProperties;
        this.simpMessageSendingOperations = simpMessageSendingOperations;
    }

    @Override
    public <T> void emit(String type, T notification) {
        Map<String, String> simpMappings = notificationProperties.getSimpMappings();
        Optional.ofNullable(simpMappings.get(type)).ifPresent(destination -> {
            Map<String, Object> payload = conversionService.convert(notification, Map.class);
            simpMessageSendingOperations.convertAndSend(destination, payload);
        });
    }
}
