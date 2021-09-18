package com.crypto.core.notifications.controllers;

import com.crypto.core.notifications.domain.NotificationRequest;
import com.crypto.core.notifications.dto.NotificationRequestDto;
import com.crypto.core.notifications.services.NotificationEmitter;
import com.crypto.core.notifications.services.NotificationService;
import org.springframework.core.convert.ConversionService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.*;

@RestController
public class NotificationController {
    private final ConversionService conversionService;
    private final NotificationEmitter notificationEmitter;
    private final NotificationService notificationService;

    public NotificationController(
            ConversionService conversionService,
            NotificationService notificationService,
            NotificationEmitter notificationEmitter
    ) {
        this.conversionService = conversionService;
        this.notificationEmitter = notificationEmitter;
        this.notificationService = notificationService;
    }

    @MessageMapping("/notify")
    public void sendNotification(@RequestBody NotificationRequestDto notificationRequestDto) {
        NotificationRequest notificationRequest = conversionService.convert(notificationRequestDto, NotificationRequest.class);
        notificationRequest = notificationService.saveNotification(notificationRequest);
        notificationEmitter.emit(notificationRequest);
    }
}
