package com.trader.core.converters;

import com.binance.api.client.domain.account.AssetBalance;
import com.trader.core.domain.FundsBalance;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class AssetBalanceToFundsBalanceConverter implements Converter<AssetBalance, FundsBalance> {
    @Override
    public FundsBalance convert(AssetBalance source) {
        FundsBalance fundsBalance = new FundsBalance();
        fundsBalance.setAsset(source.getAsset());
        fundsBalance.setFree(new BigDecimal(source.getFree()));
        fundsBalance.setLocked(new BigDecimal(source.getLocked()));
        return fundsBalance;
    }
}
