package com.crypto.core.subscriptions.repository.converters;

import com.crypto.core.subscriptions.domain.Subscription;
import com.crypto.core.subscriptions.entity.SubscriptionEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class EntityToSubscriptionConverter implements Converter<SubscriptionEntity, Subscription> {
    @Override
    public Subscription convert(SubscriptionEntity source) {
        return Subscription.newBuilder()
                .id(source.getId())
                .symbolName(source.getSymbolName())
                .build();
    }
}
