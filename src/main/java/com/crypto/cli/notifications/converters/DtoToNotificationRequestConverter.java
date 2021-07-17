package com.crypto.cli.notifications.converters;

import com.crypto.cli.notifications.domain.NotificationRequest;
import com.crypto.cli.notifications.dto.NotificationRequestDto;
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
