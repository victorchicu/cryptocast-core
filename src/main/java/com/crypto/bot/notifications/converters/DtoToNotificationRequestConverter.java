package com.crypto.bot.notifications.converters;

import com.crypto.bot.notifications.domain.NotificationRequest;
import com.crypto.bot.notifications.dto.NotificationRequestDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DtoToNotificationRequestConverter implements Converter<NotificationRequestDto, NotificationRequest> {
    @Override
    public NotificationRequest convert(NotificationRequestDto source) {
        NotificationRequest notificationRequest = new NotificationRequest();
        notificationRequest.setType(source.getType());
        notificationRequest.setPayload(source.getPayload());
        return notificationRequest;
    }
}
