package com.trader.core.binance.assets.converters;

import com.trader.core.binance.client.domain.account.AssetBalance;
import com.trader.core.binance.configs.BinanceProperties;
import com.trader.core.binance.assets.dto.AssetBalanceDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class AssetToDtoConverter implements Converter<AssetBalance, AssetBalanceDto> {
    private static final Logger LOGGER = LoggerFactory.getLogger(AssetToDtoConverter.class);

    private final BinanceProperties binanceProperties;

    public AssetToDtoConverter(@Lazy BinanceProperties binanceProperties) {
        this.binanceProperties = binanceProperties;
    }

    @Override
    public AssetBalanceDto convert(AssetBalance source) {
        return new AssetBalanceDto(
                source.getAsset(),
                source.getName(),
                source.getIcon(),
                source.getFlagged(),
                source.getFree(),
                BigDecimal.ZERO
        );
    }
}
