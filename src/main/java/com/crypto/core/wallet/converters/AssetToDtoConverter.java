package com.crypto.core.wallet.converters;

import com.crypto.core.binance.client.domain.wallet.Asset;
import com.crypto.core.binance.configs.BinanceProperties;
import com.crypto.core.wallet.dto.AssetDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;

@Component
public class AssetToDtoConverter implements Converter<Asset, AssetDto> {
    private static final Logger LOGGER = LoggerFactory.getLogger(AssetToDtoConverter.class);

    private final BinanceProperties binanceProperties;

    public AssetToDtoConverter(@Lazy BinanceProperties binanceProperties) {
        this.binanceProperties = binanceProperties;
    }

    @Override
    public AssetDto convert(Asset source) {
        return new AssetDto(
                source.getCoin(),
                source.getName(),
                getIconOrDefault(source.getCoin()),
                source.getFlagged(),
                source.getFree(),
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                BigDecimal.ZERO
        );
    }

    private Integer getIconOrDefault(String coin) {
        return Optional.ofNullable(binanceProperties.getAssets().get(coin))
                .map(symbol -> symbol.getIcon())
                .orElseGet(() -> {
                    LOGGER.warn("No image icon found for {0}", coin);
                    return 0;
                });
    }
}
