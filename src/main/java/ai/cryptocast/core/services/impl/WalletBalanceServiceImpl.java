package ai.cryptocast.core.services.impl;

import ai.cryptocast.core.domain.WalletBalance;
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
    public List<WalletBalance> list(User user, String label) {
        return Optional.ofNullable(user.getWallets().get(label))
                .map(wallet -> Optional.ofNullable(exchanges.get(wallet.getLabel())))
                .map(exchangeService ->
                        exchangeService.map(ExchangeService::listWalletBalances)
                                .orElse(Collections.emptyList())
                )
                .orElseThrow(() -> new RuntimeException("Unsupported exchange service: " + label));
    }
}