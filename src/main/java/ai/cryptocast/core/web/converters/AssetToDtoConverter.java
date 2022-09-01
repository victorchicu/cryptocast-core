package ai.cryptocast.core.web.converters;

import ai.cryptocast.core.domain.AssetBalance;
import ai.cryptocast.core.enums.ExchangeType;
import ai.cryptocast.core.web.dto.AssetBalanceDto;
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
