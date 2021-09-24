package com.crypto.core.binance.services.impl;

import com.crypto.core.binance.client.domain.market.SymbolPrice;
import com.crypto.core.binance.client.SubscriptionClient;
import com.crypto.core.binance.client.SubscriptionErrorHandler;
import com.crypto.core.binance.client.SubscriptionListener;
import com.crypto.core.binance.client.SyncRequestClient;
import com.crypto.core.binance.client.domain.event.SymbolTickerEvent;
import com.crypto.core.binance.configs.BinanceProperties;
import com.crypto.core.binance.configs.CryptoMappings;
import com.crypto.core.binance.services.BinanceService;
import com.crypto.core.exceptions.SymbolNotFoundException;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.collections4.ListUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BinanceServiceImpl implements BinanceService {
    private final BinanceProperties binanceProperties;
    private final SyncRequestClient syncRequestClient;
    private final SubscriptionClient subscriptionClient;

    public BinanceServiceImpl(
            BinanceProperties binanceProperties,
            SyncRequestClient syncRequestClient,
            SubscriptionClient subscriptionClient
    ) {
        this.binanceProperties = binanceProperties;
        this.syncRequestClient = syncRequestClient;
        this.subscriptionClient = subscriptionClient;
    }

    @Override
    public void subscribe(
            String requesterId,
            String symbolName,
            SubscriptionListener<SymbolTickerEvent> callback,
            SubscriptionErrorHandler errorHandler
    ) {
        //TODO: add connection support for multiple symbols by the requester
        findSymbol(symbolName).ifPresentOrElse(
                symbol -> subscriptionClient.subscribeSymbolTickerEvent(requesterId, symbol, callback, errorHandler),
                SymbolNotFoundException::new
        );
    }

    @Override
    public void unsubscribe(String requesterId, String symbolName) {
        //TODO: unsubscribe by requester id and symbol name
        findSymbol(symbolName).ifPresentOrElse(
                symbol -> subscriptionClient.unsubscribe(requesterId, symbol),
                SymbolNotFoundException::new
        );
    }

    @Override
    public SymbolPrice getSymbolPrice(String symbolName) {
        return findSymbol(symbolName)
                .map(symbol -> IterableUtils.first(getSymbolPrices(symbol)))
                .orElseThrow(SymbolNotFoundException::new);
    }

    @Override
    public List<SymbolPrice> getSymbolPrices(String... symbolNames) {
        return syncRequestClient.getSymbolPriceTicker(String.join(" ", symbolNames));
    }


    private Optional<String> findSymbol(String symbolName) {
        return Optional.ofNullable(binanceProperties.getCryptoMappings().get(symbolName))
                .map(CryptoMappings::getName);
    }
}
