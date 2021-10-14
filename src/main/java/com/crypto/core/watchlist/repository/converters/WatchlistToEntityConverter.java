package com.crypto.core.watchlist.repository.converters;

import com.crypto.core.watchlist.domain.Subscription;
import com.crypto.core.watchlist.entity.SubscriptionEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class WatchlistToEntityConverter implements Converter<Subscription, SubscriptionEntity> {
    @Override
    public SubscriptionEntity convert(Subscription source) {
        return SubscriptionEntity.newBuilder()
                .symbolName(source.getAssetName())
                .build();
    }
}
