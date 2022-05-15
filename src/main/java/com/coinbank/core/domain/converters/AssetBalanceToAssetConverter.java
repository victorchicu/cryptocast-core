package com.coinbank.core.domain.converters;

import com.coinbank.core.configs.BinanceConfig;
import com.coinbank.core.configs.BinanceProperties;
import com.coinbank.core.domain.Asset;
import com.coinbank.core.enums.Exchange;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;

@Component
public class AssetBalanceToAssetConverter implements Converter<com.binance.api.client.domain.account.AssetBalance, Asset> {
    private final BinanceConfig binanceConfig;

    public AssetBalanceToAssetConverter(BinanceConfig binanceConfig) {
        this.binanceConfig = binanceConfig;
    }

    @Override
    public Asset convert(com.binance.api.client.domain.account.AssetBalance source) {
        BigDecimal free = new BigDecimal(source.getFree());
        BigDecimal locked = new BigDecimal(source.getLocked());
        return Asset.newBuilder()
                .name(source.getAsset())
                .fullName(toAssetFullNameOrDefault(source.getAsset()))
                .exchange(Exchange.BINANCE)
                .totalFunds(free.add(locked))
                .fundsAvailable(free)
                .usedInAnyOutstandingOrders(locked)
                .build();
    }

    private String toAssetFullNameOrDefault(String assetName) {
        return Optional.ofNullable(binanceConfig.getProps().getAssets().get(assetName))
                .map(BinanceProperties.AssetConfig::getFullName)
                .orElse(assetName);
    }
}
