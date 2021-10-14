package com.crypto.core.watchlist.repository.converters;

import com.crypto.core.watchlist.domain.Subscription;
import com.crypto.core.watchlist.entity.SubscriptionEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class EntityToWatchlistConverter implements Converter<SubscriptionEntity, Subscription> {
    @Override
    public Subscription convert(SubscriptionEntity source) {
        return Subscription.newBuilder()
                .id(source.getId())
                .assetName(source.getAssetName())
                .build();
    }
}
