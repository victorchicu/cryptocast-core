package com.crypto.core.notifications.converters;

import com.crypto.core.notifications.domain.Notification;
import com.crypto.core.notifications.dto.NotificationDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class NotificationToDtoConverter implements Converter<Notification, NotificationDto> {
    @Override
    public NotificationDto convert(Notification source) {
        return new NotificationDto(source.getId());
    }
}
