package com.coinbank.core.configs;

import com.coinbank.core.services.ExchangeService;
import com.coinbank.core.enums.ExchangeProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class ExchangeServiceConfig {
    private final Map<String, ExchangeService> exchanges;

    public ExchangeServiceConfig(Map<String, ExchangeService> exchanges) {
        this.exchanges = exchanges;
    }

    @Bean
    public Map<ExchangeProvider, ExchangeService> exchangeProviders() {
        return exchanges.entrySet().stream()
                .collect(Collectors.toMap(
                        entryKey -> ExchangeProvider.valueOf(entryKey.getKey()),
                        Map.Entry::getValue)
                );
    }
}
