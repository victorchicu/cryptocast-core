package com.trader.core.converters;

import com.trader.core.domain.Subscription;
import com.trader.core.dto.SubscriptionDto;
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
