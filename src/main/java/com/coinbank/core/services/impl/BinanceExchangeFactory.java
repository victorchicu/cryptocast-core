package com.coinbank.core.services.impl;

import com.binance.api.client.BinanceApiClientFactory;
import com.coinbank.core.services.ExchangeFactory;
import com.coinbank.core.services.ExchangeService;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service("BINANCE")
public class BinanceExchangeFactory implements ExchangeFactory {
    private final ConversionService conversionService;

    public BinanceExchangeFactory(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @Override
    public ExchangeService create(String apiKey, String secretKey) {
        //TODO: new instance should be singleton
        BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance(apiKey, secretKey);
        return new BinanceExchangeService(conversionService, factory.newRestClient(), factory.newWebSocketClient());
    }
}
