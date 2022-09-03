package ai.cryptocast.core.services.impl;

import ai.cryptocast.core.domain.Wallet;
import ai.cryptocast.core.services.ExchangeProvider;
import ai.cryptocast.core.services.ExchangeService;
import ai.cryptocast.core.services.ExchangeFactory;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class ExchangeProviderImpl implements ExchangeProvider {
    private final Map<String, ExchangeFactory> exchangeFactory;

    public ExchangeProviderImpl(Map<String, ExchangeFactory> exchangeFactory) {
        this.exchangeFactory = exchangeFactory;
    }

    @Override
    public ExchangeService get(Wallet wallet) {
        return Optional.ofNullable(exchangeFactory.get(wallet.getExchange().name()))
                .map(factory -> factory.create(wallet.getApiKey(), wallet.getSecretKey()))
                .orElseThrow(() -> new RuntimeException("Unsupported exchange factory: " + wallet.getExchange()));
    }
}
