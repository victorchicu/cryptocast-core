package com.trader.core.subscriptions.converters;

import com.trader.core.subscriptions.domain.Subscription;
import com.trader.core.subscriptions.dto.SubscriptionDto;
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
