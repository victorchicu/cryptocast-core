package com.coinbank.core.domain.services.impl;

import com.binance.api.client.impl.BinanceApiRestClientImpl;
import com.coinbank.core.domain.configs.BinanceConfig;
import com.coinbank.core.domain.services.ExchangeFactory;
import com.coinbank.core.domain.services.ExchangeService;
import com.coinbank.core.domain.services.NotificationTemplate;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service("BINANCE")
public class BinanceExchangeFactory implements ExchangeFactory {
    private final BinanceConfig binanceConfig;
    private final ConversionService conversionService;
    private final NotificationTemplate notificationTemplate;

    public BinanceExchangeFactory(BinanceConfig binanceConfig, ConversionService conversionService, NotificationTemplate notificationTemplate) {
        this.binanceConfig = binanceConfig;
        this.conversionService = conversionService;
        this.notificationTemplate = notificationTemplate;
    }

    @Override
    public ExchangeService create(String apiKey, String secretKey) {
        return new BinanceExchangeService(binanceConfig, conversionService, notificationTemplate, new BinanceApiRestClientImpl(apiKey, secretKey));
    }
}
