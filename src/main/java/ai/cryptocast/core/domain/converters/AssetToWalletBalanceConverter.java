package ai.cryptocast.core.domain.converters;

import ai.cryptocast.core.domain.WalletBalance;
import ai.cryptocast.core.enums.ExchangeType;
import com.binance.api.client.domain.account.AssetBalance;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class AssetToWalletBalanceConverter implements Converter<AssetBalance, WalletBalance> {
    @Override
    public WalletBalance convert(AssetBalance source) {
        BigDecimal free = new BigDecimal(source.getFree());
        BigDecimal locked = new BigDecimal(source.getLocked());
        return WalletBalance.newBuilder()
                .name(source.getAsset())
                .fullName(source.getAsset())
                .exchange(ExchangeType.BINANCE)
                .totalFunds(free.add(locked))
                .fundsAvailable(free)
                .usedInAnyOutstandingOrders(locked)
                .build();
    }
}