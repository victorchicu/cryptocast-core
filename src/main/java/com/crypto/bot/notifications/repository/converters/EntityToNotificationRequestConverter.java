package com.crypto.bot.notifications.repository.converters;

import com.crypto.bot.notifications.domain.NotificationRequest;
import com.crypto.bot.notifications.repository.entity.NotificationRequestEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class EntityToNotificationRequestConverter implements Converter<NotificationRequestEntity, NotificationRequest> {
    @Override
    public NotificationRequest convert(NotificationRequestEntity source) {
        NotificationRequest notificationRequest = new NotificationRequest();
        notificationRequest.setId(source.getId());
        notificationRequest.setType(source.getType());
        notificationRequest.setOwner(source.getOwner());
        notificationRequest.setPayload(source.getPayload());
        return notificationRequest;
    }
}
