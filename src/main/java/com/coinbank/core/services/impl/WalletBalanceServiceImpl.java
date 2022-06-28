package com.coinbank.core.services.impl;

import com.coinbank.core.domain.AssetBalance;
import com.coinbank.core.domain.User;
import com.coinbank.core.services.WalletBalanceService;
import com.coinbank.core.services.ExchangeService;
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