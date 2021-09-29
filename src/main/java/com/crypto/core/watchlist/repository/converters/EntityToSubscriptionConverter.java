package com.crypto.core.watchlist.repository.converters;

import com.crypto.core.watchlist.domain.Watchlist;
import com.crypto.core.watchlist.entity.WatchlistEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class EntityToSubscriptionConverter implements Converter<WatchlistEntity, Watchlist> {
    @Override
    public Watchlist convert(WatchlistEntity source) {
        return Watchlist.newBuilder()
                .id(source.getId())
                .symbolName(source.getSymbolName())
                .build();
    }
}
