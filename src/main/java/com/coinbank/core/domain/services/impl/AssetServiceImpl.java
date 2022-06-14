package com.coinbank.core.domain.services.impl;

import com.coinbank.core.domain.Asset;
import com.coinbank.core.domain.User;
import com.coinbank.core.domain.services.AssetService;
import com.coinbank.core.domain.services.ExchangeService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AssetServiceImpl implements AssetService {
    private final Map<String, ExchangeService> exchanges;

    public AssetServiceImpl(Map<String, ExchangeService> exchanges) {
        this.exchanges = exchanges;
    }

    @Override
    public List<Asset> listAssets(User user, String label) {
        return Optional.ofNullable(user.getExchanges().get(label))
                .map(apiKey -> Optional.ofNullable(exchanges.get(apiKey.getLabel())))
                .map(optionalExchange ->
                        optionalExchange.map(ExchangeService::listAssets)
                                .orElse(Collections.emptyList())
                )
                .orElseThrow(() -> new RuntimeException("Unsupported exchange service: " + label));
    }
}