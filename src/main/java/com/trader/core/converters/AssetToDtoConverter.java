package com.trader.core.converters;

import com.trader.core.dto.AssetBalanceDto;
import com.trader.core.exceptions.AssetNotFoundException;
import com.trader.core.binance.domain.account.AssetBalance;
import com.trader.core.configs.BinanceProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AssetToDtoConverter implements Converter<AssetBalance, AssetBalanceDto> {
    private static final Logger LOGGER = LoggerFactory.getLogger(AssetToDtoConverter.class);

    private final BinanceProperties binanceProperties;

    public AssetToDtoConverter(@Lazy BinanceProperties binanceProperties) {
        this.binanceProperties = binanceProperties;
    }

    @Override
    public AssetBalanceDto convert(AssetBalance source) {
        BinanceProperties.AssetConfig assetConfig = findAssetByName(source.getAsset());
        return new AssetBalanceDto(
                source.getAsset(),
                assetConfig.getFullName(),
                assetConfig.getIcon(),
                source.getFlagged(),
                source.getFree(),
                source.getLocked(),
                source.getPrice(),
                source.getBalance(),
                source.getQuotation()
        );
    }

    private BinanceProperties.AssetConfig findAssetByName(String assetName) {
        return Optional.ofNullable(binanceProperties.getAssets().get(assetName))
                .orElseThrow(AssetNotFoundException::new);
    }
}
