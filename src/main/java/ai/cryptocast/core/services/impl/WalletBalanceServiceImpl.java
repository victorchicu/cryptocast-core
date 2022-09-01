package ai.cryptocast.core.services.impl;

import ai.cryptocast.core.domain.AssetBalance;
import ai.cryptocast.core.domain.User;
import ai.cryptocast.core.services.ExchangeService;
import ai.cryptocast.core.services.WalletBalanceService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class WalletBalanceServiceImpl implements WalletBalanceService {
    private final Map<String, ExchangeService> exchanges;

    public WalletBalanceServiceImpl(Map<String, ExchangeService> exchanges) {
        this.exchanges = exchanges;
    }

    @Override
    public List<AssetBalance> list(User user, String label) {
        return Optional.ofNullable(user.getExchanges().get(label))
                .map(apiKey -> Optional.ofNullable(exchanges.get(apiKey.getLabel())))
                .map(exchangeService -> exchangeService.map(ExchangeService::listAssetBalances)
                        .orElse(Collections.emptyList())
                )
                .orElseThrow(() -> new RuntimeException("Unsupported exchange service: " + label));
    }
}