package com.crypto.core.wallet.converters;

import com.crypto.core.binance.client.domain.wallet.Asset;
import com.crypto.core.binance.configs.BinanceProperties;
import com.crypto.core.wallet.dto.AssetDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;

@Component
public class AssetToDtoConverter implements Converter<Asset, AssetDto> {
    private final BinanceProperties binanceProperties;

    public AssetToDtoConverter(BinanceProperties binanceProperties) {
        this.binanceProperties = binanceProperties;
    }

    @Override
    public AssetDto convert(Asset source) {
        return new AssetDto(
                source.getCoin(),
                source.getName(),
                getIconOrDefault(source.getCoin()),
                new BigDecimal(source.getFree())
        );
    }

    private Integer getIconOrDefault(String coin) {
        return Optional.ofNullable(binanceProperties.getAssets().get(coin))
                .map(symbol -> symbol.getIcon())
                .orElse(0);
    }
}
