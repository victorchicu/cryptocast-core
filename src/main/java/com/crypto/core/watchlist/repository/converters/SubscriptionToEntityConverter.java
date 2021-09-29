package com.crypto.core.watchlist.repository.converters;

import com.crypto.core.watchlist.domain.Watchlist;
import com.crypto.core.watchlist.entity.WatchlistEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SubscriptionToEntityConverter implements Converter<Watchlist, WatchlistEntity> {
    @Override
    public WatchlistEntity convert(Watchlist source) {
        return WatchlistEntity.newBuilder()
                .symbolName(source.getSymbolName())
                .build();
    }
}
