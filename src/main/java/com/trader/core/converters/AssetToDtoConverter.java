package com.trader.core.converters;

import com.trader.core.configs.BinanceProperties;
import com.trader.core.domain.Asset;
import com.trader.core.dto.AssetDto;
import com.trader.core.exceptions.AssetNotFoundException;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AssetToDtoConverter implements Converter<Asset, AssetDto> {
    private final BinanceProperties binanceProperties;

    public AssetToDtoConverter(@Lazy BinanceProperties binanceProperties) {
        this.binanceProperties = binanceProperties;
    }

    @Override
    public AssetDto convert(Asset source) {
        BinanceProperties.AssetConfig assetConfig = findAssetConfig(source.getAsset());
        return new AssetDto(
                source.getAsset(),
                assetConfig.getFullName(),
                assetConfig.getIconIndex(),
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
