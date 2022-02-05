package com.trader.core.repository.converters;

import com.trader.core.domain.Subscription;
import com.trader.core.entity.SubscriptionEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class EntityToSubscriptionConverter implements Converter<SubscriptionEntity, Subscription> {
    @Override
    public Subscription convert(SubscriptionEntity source) {
        return Subscription.newBuilder()
                .id(source.getId())
                .assetName(source.getAssetName())
                .build();
    }
}
