package com.trader.core.converters;

import com.trader.core.domain.AssetBalance;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class BinanceToAssetBalanceConverter implements Converter<com.binance.api.client.domain.account.AssetBalance, AssetBalance> {
    @Override
    public AssetBalance convert(com.binance.api.client.domain.account.AssetBalance source) {
        AssetBalance assetBalance = new AssetBalance();
        assetBalance.setAsset(source.getAsset());
        assetBalance.setFree(new BigDecimal(source.getFree()));
        assetBalance.setLocked(new BigDecimal(source.getLocked()));
        return assetBalance;
    }
}
