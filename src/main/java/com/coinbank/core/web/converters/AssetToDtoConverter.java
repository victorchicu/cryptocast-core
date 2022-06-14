package com.coinbank.core.web.converters;

import com.coinbank.core.domain.Asset;
import com.coinbank.core.enums.ExchangeType;
import com.coinbank.core.web.dto.AssetDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AssetToDtoConverter implements Converter<Asset, AssetDto> {
    @Override
    public AssetDto convert(Asset source) {
        return new AssetDto(
                source.getName(),
                source.getFullName(),
                ExchangeType.BINANCE,
                source.getTotalFunds(),
                source.getFundsAvailable(),
                source.getUsedInAnyOutstandingOrders()
        );
    }
}
