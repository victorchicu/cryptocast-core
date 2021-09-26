package com.crypto.core.subscriptions.repository.converters;

import com.crypto.core.subscriptions.domain.Subscription;
import com.crypto.core.subscriptions.entity.SubscriptionEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SubscriptionToEntityConverter implements Converter<Subscription, SubscriptionEntity> {
    @Override
    public SubscriptionEntity convert(Subscription source) {
        return SubscriptionEntity.newBuilder()
                .symbolName(source.getSymbolName())
                .build();
    }
}
