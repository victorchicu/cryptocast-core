package com.coinbank.core.services.impl;

import com.coinbank.core.domain.Notification;
import com.coinbank.core.services.NotificationService;
import com.coinbank.core.repository.entity.NotificationEntity;
import com.coinbank.core.repository.NotificationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.core.convert.ConversionService;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Service
public class NotificationServiceImpl implements NotificationService {
    private static final Logger LOG = LoggerFactory.getLogger(NotificationServiceImpl.class);
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
        LOG.trace("Session connected event {}", sessionConnectedEvent);
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent sessionDisconnectEvent) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(sessionDisconnectEvent.getMessage());
        LOG.trace("Session disconnected event {}", headerAccessor);
    }


    private Notification toNotification(NotificationEntity notificationEntity) {
        return conversionService.convert(notificationEntity, Notification.class);
    }

    private <T> NotificationEntity toNotificationEntity(T notification) {
        return conversionService.convert(notification, NotificationEntity.class);
    }
}
