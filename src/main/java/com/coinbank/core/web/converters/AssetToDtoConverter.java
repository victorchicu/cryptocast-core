package com.coinbank.core.web.converters;

import com.coinbank.core.domain.AssetBalance;
import com.coinbank.core.enums.ExchangeType;
import com.coinbank.core.web.dto.AssetBalanceDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AssetToDtoConverter implements Converter<AssetBalance, AssetBalanceDto> {
    @Override
    public AssetBalanceDto convert(AssetBalance source) {
        return new AssetBalanceDto(
                source.getName(),
                source.getFullName(),
                ExchangeType.BINANCE,
                source.getTotalFunds(),
                source.getFundsAvailable(),
                source.getUsedInAnyOutstandingOrders()
        );
    }
}
