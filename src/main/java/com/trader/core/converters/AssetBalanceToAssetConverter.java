package com.trader.core.converters;

import com.trader.core.domain.Asset;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class AssetBalanceToAssetConverter implements Converter<com.binance.api.client.domain.account.AssetBalance, Asset> {
    @Override
    public Asset convert(com.binance.api.client.domain.account.AssetBalance source) {
        Asset asset = new Asset();
        asset.setAsset(source.getAsset());
        asset.setFree(new BigDecimal(source.getFree()));
        asset.setLocked(new BigDecimal(source.getLocked()));
        return asset;
    }
}
