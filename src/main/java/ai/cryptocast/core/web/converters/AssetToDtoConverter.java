package ai.cryptocast.core.web.converters;

import ai.cryptocast.core.domain.WalletBalance;
import ai.cryptocast.core.enums.Exchange;
import ai.cryptocast.core.web.dto.WalletBalanceDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AssetToDtoConverter implements Converter<WalletBalance, WalletBalanceDto> {
    @Override
    public WalletBalanceDto convert(WalletBalance source) {
        return new WalletBalanceDto(
                source.getName(),
                source.getFullName(),
                Exchange.BINANCE,
                source.getTotalFunds(),
                source.getFundsAvailable(),
                source.getUsedInAnyOutstandingOrders()
        );
    }
}
