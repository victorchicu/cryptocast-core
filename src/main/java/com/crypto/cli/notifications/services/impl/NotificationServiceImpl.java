package com.crypto.cli.notifications.services.impl;

import com.crypto.cli.notifications.domain.NotificationRequest;
import com.crypto.cli.notifications.repository.NotificationRepository;
import com.crypto.cli.notifications.repository.entity.NotificationRequestEntity;
import com.crypto.cli.notifications.services.NotificationService;
import org.springframework.context.event.EventListener;
import org.springframework.core.convert.ConversionService;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Service
public class NotificationServiceImpl implements NotificationService {
    private final ConversionService conversionService;
    private final NotificationRepository notificationRepository;

    public NotificationServiceImpl(ConversionService conversionService, NotificationRepository notificationRepository) {
        this.conversionService = conversionService;
        this.notificationRepository = notificationRepository;
    }

    @Override
    public NotificationRequest saveNotification(NotificationRequest notificationRequest) {
        NotificationRequestEntity notificationRequestEntity = toNotificationRequestEntity(notificationRequest);
        return toNotificationRequest(notificationRepository.save(notificationRequestEntity));
    }

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent sessionConnectedEvent) {
        System.out.println(sessionConnectedEvent);
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent sessionDisconnectEvent) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(sessionDisconnectEvent.getMessage());
        System.out.println(headerAccessor);
    }


    private NotificationRequest toNotificationRequest(NotificationRequestEntity notificationRequestEntity) {
        return conversionService.convert(notificationRequestEntity, NotificationRequest.class);
    }

    private NotificationRequestEntity toNotificationRequestEntity(NotificationRequest notificationRequest) {
        return conversionService.convert(notificationRequest, NotificationRequestEntity.class);
    }
}
