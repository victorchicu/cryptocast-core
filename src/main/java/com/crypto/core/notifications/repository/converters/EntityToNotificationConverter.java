package com.crypto.core.notifications.repository.converters;

import com.crypto.core.notifications.domain.Notification;
import com.crypto.core.notifications.repository.entity.NotificationEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class EntityToNotificationConverter implements Converter<NotificationEntity, Notification> {
    @Override
    public Notification convert(NotificationEntity source) {
        return Notification.newBuilder()
                .id(source.getId())
                .event(source.getEvent())
                .payload(source.getPayload())
                .build();
    }
}
