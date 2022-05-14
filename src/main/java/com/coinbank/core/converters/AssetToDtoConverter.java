package com.coinbank.core.converters;

import com.coinbank.core.domain.Asset;
import com.coinbank.core.dto.AssetDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AssetToDtoConverter implements Converter<Asset, AssetDto> {
    private static final Logger LOG = LoggerFactory.getLogger(AssetToDtoConverter.class);

    @Override
    public AssetDto convert(Asset source) {
        return new AssetDto(
                source.getName(),
                source.getFullName(),
                source.getApiKeyName(),
                source.getExchange(),
                source.getTotalFunds(),
                source.getFundsAvailable(),
                source.getUsedInAnyOutstandingOrders()
        );
    }
}
