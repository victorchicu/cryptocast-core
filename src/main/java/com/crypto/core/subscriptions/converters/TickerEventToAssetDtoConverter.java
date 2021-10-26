package com.crypto.core.subscriptions.converters;

import com.crypto.core.binance.client.domain.event.TickerEvent;
import com.crypto.core.binance.dto.AssetBalanceDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TickerEventToAssetDtoConverter implements Converter<TickerEvent, AssetBalanceDto> {
    @Override
    public AssetBalanceDto convert(TickerEvent source) {
        return null;
    }
}
