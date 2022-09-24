package ai.cryptocast.core.services.impl;

import ai.cryptocast.core.services.ExchangeService;
import ai.cryptocast.core.services.ExchangeFactory;
import ai.cryptocast.core.services.NotificationTemplate;
import com.binance.api.client.BinanceApiClientFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service("BINANCE")
public class BinanceExchangeFactory implements ExchangeFactory {
    private final ConversionService conversionService;
    private final NotificationTemplate notificationTemplate;

    public BinanceExchangeFactory(ConversionService conversionService, NotificationTemplate notificationTemplate) {
        this.conversionService = conversionService;
        this.notificationTemplate = notificationTemplate;
    }

    @Override
    @Cacheable
    public ExchangeService create(String apiKey, String secretKey) {
        BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance(apiKey, secretKey);
        return new BinanceExchangeService(conversionService, notificationTemplate, factory.newRestClient(), factory.newWebSocketClient());
    }
}