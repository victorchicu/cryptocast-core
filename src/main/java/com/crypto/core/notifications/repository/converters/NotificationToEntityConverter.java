package com.crypto.core.notifications.repository.converters;

import com.crypto.core.notifications.domain.Notification;
import com.crypto.core.notifications.repository.entity.NotificationEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class NotificationToEntityConverter implements Converter<Notification, NotificationEntity> {
    @Override
    public NotificationEntity convert(Notification source) {
        return NotificationEntity.newBuilder()
                .event(source.getEvent())
                .payload(source.getPayload())
                .build();
    }
}
