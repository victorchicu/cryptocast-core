package com.crypto.core.notifications.services.impl;

import com.crypto.core.notifications.configs.NotificationProperties;
import com.crypto.core.notifications.enums.NotificationEvent;
import com.crypto.core.notifications.services.NotificationEmitter;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.util.CastUtils;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Map;

@Service
public class SimpNotificationEmitter implements NotificationEmitter {
    private final ConversionService conversionService;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final NotificationProperties notificationProperties;

    public SimpNotificationEmitter(
            ConversionService conversionService,
            SimpMessagingTemplate simpMessagingTemplate,
            NotificationProperties notificationProperties
    ) {
        this.conversionService = conversionService;
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.notificationProperties = notificationProperties;
    }

    @Override
    public <T> T emitNotification(Principal principal, NotificationEvent type, T payload, Class<?> classType) {
        Map<NotificationEvent, String> simpMappings = notificationProperties.getSimpMappings();
        String destination = simpMappings.get(type);
        if (destination != null) {
            simpMessagingTemplate.convertAndSend(destination, conversionService.convert(payload, classType));
        }
        return payload;
    }
}
