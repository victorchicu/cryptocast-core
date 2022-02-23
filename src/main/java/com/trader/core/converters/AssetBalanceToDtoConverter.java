package com.trader.core.converters;

import com.trader.core.configs.BinanceProperties;
import com.trader.core.domain.AssetBalance;
import com.trader.core.dto.AssetBalanceDto;
import com.trader.core.exceptions.AssetNotFoundException;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AssetBalanceToDtoConverter implements Converter<AssetBalance, AssetBalanceDto> {
    private final BinanceProperties binanceProperties;

    public AssetBalanceToDtoConverter(@Lazy BinanceProperties binanceProperties) {
        this.binanceProperties = binanceProperties;
    }

    @Override
    public AssetBalanceDto convert(AssetBalance source) {
        BinanceProperties.AssetConfig assetConfig = findAssetConfig(source.getAsset());
        return new AssetBalanceDto(
                source.getAsset(),
                assetConfig.getFullName(),
                source.getFree(),
                source.getLocked(),
                source.getPrice(),
                source.getPriceChange(),
                source.getBalance(),
                source.getQuotation()
        );
    }

    private BinanceProperties.AssetConfig findAssetConfig(String assetName) {
        return Optional.ofNullable(binanceProperties.getAssets().get(assetName))
                .orElseThrow(() -> new AssetNotFoundException(assetName));
    }
}
