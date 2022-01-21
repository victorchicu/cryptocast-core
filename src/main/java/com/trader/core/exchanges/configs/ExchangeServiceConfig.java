package com.trader.core.exchanges.configs;

import com.trader.core.enums.ExchangeProvider;
import com.trader.core.exchanges.ExchangeProviderService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class ExchangeServiceConfig {
    private final Map<String, ExchangeProviderService> exchangeProviders;

    public ExchangeServiceConfig(Map<String, ExchangeProviderService> exchangeProviders) {
        this.exchangeProviders = exchangeProviders;
    }

    @Bean
    public Map<ExchangeProvider, ExchangeProviderService> exchangeProviders() {
        return exchangeProviders.entrySet().stream()
                .collect(Collectors.toMap(
                        entryKey -> ExchangeProvider.valueOf(entryKey.getKey()),
                        Map.Entry::getValue)
                );
    }
}
