package com.crypto.core.watchlist.converters;

import com.crypto.core.watchlist.domain.Subscription;
import com.crypto.core.watchlist.dto.SubscriptionDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SubscriptionToDtoConverter implements Converter<Subscription, SubscriptionDto> {
    @Override
    public SubscriptionDto convert(Subscription source) {
        return new SubscriptionDto(
                source.getId(),
                source.getAssetName()
        );
    }
}
