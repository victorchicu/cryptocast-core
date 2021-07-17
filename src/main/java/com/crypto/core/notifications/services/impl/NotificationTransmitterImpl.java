package com.crypto.core.notifications.services.impl;

import com.crypto.core.notifications.configs.NotificationProperties;
import com.crypto.core.notifications.domain.NotificationRequest;
import com.crypto.core.notifications.dto.NotificationRequestDto;
import com.crypto.core.notifications.services.NotificationTransmitter;
import org.springframework.core.convert.ConversionService;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class NotificationTransmitterImpl implements NotificationTransmitter {
    private final ConversionService conversionService;
    private final NotificationProperties notificationProperties;
    private final SimpMessageSendingOperations simpMessageSendingOperations;

    public NotificationTransmitterImpl(
            ConversionService conversionService,
            NotificationProperties notificationProperties,
            SimpMessageSendingOperations simpMessageSendingOperations
    ) {
        this.conversionService = conversionService;
        this.notificationProperties = notificationProperties;
        this.simpMessageSendingOperations = simpMessageSendingOperations;
    }

    @Override
    public void sendNotification(NotificationRequest notificationRequest) {
        Map<String, String> mappings = notificationProperties.getMappings();

        String destination = mappings.get(notificationRequest.getType());

        NotificationRequestDto notificationRequestDto = conversionService.convert(
                notificationRequest,
                NotificationRequestDto.class
        );

        simpMessageSendingOperations.convertAndSend(
                destination,
                notificationRequestDto
        );
    }
}
