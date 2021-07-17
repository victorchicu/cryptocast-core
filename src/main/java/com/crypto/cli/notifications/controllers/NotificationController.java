package com.crypto.cli.notifications.controllers;

import com.crypto.cli.notifications.domain.NotificationRequest;
import com.crypto.cli.notifications.dto.NotificationRequestDto;
import com.crypto.cli.notifications.services.NotificationTransmitter;
import com.crypto.cli.notifications.services.NotificationService;
import org.springframework.core.convert.ConversionService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.*;

@RestController
public class NotificationController {
    private final ConversionService conversionService;
    private final NotificationTransmitter notificationTransmitter;
    private final NotificationService notificationService;

    public NotificationController(
            ConversionService conversionService,
            NotificationTransmitter notificationTransmitter,
            NotificationService notificationService
    ) {
        this.conversionService = conversionService;
        this.notificationTransmitter = notificationTransmitter;
        this.notificationService = notificationService;
    }

    @MessageMapping("/notify")
    public void sendNotification(@RequestBody NotificationRequestDto notificationRequestDto) {
        NotificationRequest rawNotificationRequest = conversionService.convert(
                notificationRequestDto,
                NotificationRequest.class
        );

        NotificationRequest notificationRequest = notificationService.saveNotification(
                rawNotificationRequest
        );

        notificationTransmitter.sendNotification(notificationRequest);
    }
}
