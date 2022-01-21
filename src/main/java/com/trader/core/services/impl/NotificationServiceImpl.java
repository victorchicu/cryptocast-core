package com.trader.core.services.impl;

import com.trader.core.domain.Notification;
import com.trader.core.repository.NotificationRepository;
import com.trader.core.entity.NotificationEntity;
import com.trader.core.services.NotificationService;
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
    public <T> Notification saveNotification(T payload) {
        NotificationEntity notificationEntity = toNotificationEntity(payload);
        notificationEntity = notificationRepository.save(notificationEntity);
        return toNotification(notificationEntity);
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


    private Notification toNotification(NotificationEntity notificationEntity) {
        return conversionService.convert(notificationEntity, Notification.class);
    }

    private <T> NotificationEntity toNotificationEntity(T notification) {
        return conversionService.convert(notification, NotificationEntity.class);
    }
}
