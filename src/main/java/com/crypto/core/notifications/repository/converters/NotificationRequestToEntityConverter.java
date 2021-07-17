package com.crypto.core.notifications.repository.converters;

import com.crypto.core.notifications.domain.NotificationRequest;
import com.crypto.core.notifications.repository.entity.NotificationRequestEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class NotificationRequestToEntityConverter implements Converter<NotificationRequest, NotificationRequestEntity> {
    @Override
    public NotificationRequestEntity convert(NotificationRequest source) {
        NotificationRequestEntity notificationRequestEntity = new NotificationRequestEntity();
        notificationRequestEntity.setType(source.getType());
        notificationRequestEntity.setPayload(source.getPayload());
        return notificationRequestEntity;
    }
}
