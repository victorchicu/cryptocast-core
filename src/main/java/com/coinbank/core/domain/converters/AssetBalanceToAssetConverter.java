package com.coinbank.core.domain.converters;

import com.coinbank.core.domain.configs.BinanceConfig;
import com.coinbank.core.domain.configs.BinanceProperties;
import com.coinbank.core.domain.Asset;
import com.coinbank.core.enums.ExchangeType;
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
                .fullName(source.getAsset())
                .exchange(ExchangeType.BINANCE)
                .totalFunds(free.add(locked))
                .fundsAvailable(free)
                .usedInAnyOutstandingOrders(locked)
                .build();
    }
}
