package com.trader.core.subscriptions.repository.converters;

import com.trader.core.subscriptions.domain.Subscription;
import com.trader.core.subscriptions.entity.SubscriptionEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SubscriptionToEntityConverter implements Converter<Subscription, SubscriptionEntity> {
    @Override
    public SubscriptionEntity convert(Subscription source) {
        return SubscriptionEntity.newBuilder()
                .symbolName(source.getAssetName())
                .build();
    }
}
