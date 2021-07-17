package com.crypto.cli.notifications.converters;

import com.crypto.cli.notifications.domain.NotificationRequest;
import com.crypto.cli.notifications.dto.NotificationRequestDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class NotificationRequestToDtoConverter implements Converter<NotificationRequest, NotificationRequestDto> {
    @Override
    public NotificationRequestDto convert(NotificationRequest source) {
        return new NotificationRequestDto(source.getId(), source.getType(), source.getPayload());
    }
}
