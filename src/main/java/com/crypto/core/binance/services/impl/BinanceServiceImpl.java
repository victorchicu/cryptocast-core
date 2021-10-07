package com.crypto.core.binance.services.impl;

import com.crypto.core.binance.client.domain.market.PriceChangeTicker;
import com.crypto.core.binance.client.domain.market.SymbolPrice;
import com.crypto.core.binance.client.SubscriptionClient;
import com.crypto.core.binance.client.SubscriptionErrorHandler;
import com.crypto.core.binance.client.SubscriptionListener;
import com.crypto.core.binance.client.SyncRequestClient;
import com.crypto.core.binance.client.domain.event.SymbolTickerEvent;
import com.crypto.core.binance.configs.BinanceProperties;
import com.crypto.core.binance.services.BinanceService;
import com.crypto.core.binance.exceptions.SymbolNotFoundException;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.stereotype.Service;

import java.security.Principal;
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
            Principal principal,
            String symbolName,
            SubscriptionListener<SymbolTickerEvent> subscriptionListener,
            SubscriptionErrorHandler errorHandler
    ) {
        //TODO: add connection support for multiple symbols by the requester
        findSymbol(symbolName).ifPresentOrElse(
                symbol -> subscriptionClient.subscribeSymbolTickerEvent(principal.getName(), symbol, subscriptionListener, errorHandler),
                () -> new SymbolNotFoundException(symbolName)
        );
    }

    @Override
    public void unsubscribe(Principal principal, String symbolName) {
        //TODO: unsubscribe by requester id and symbol name
        findSymbol(symbolName).ifPresentOrElse(
                symbol -> subscriptionClient.unsubscribe(principal.getName(), symbol),
                () -> new SymbolNotFoundException(symbolName)
        );
    }

    @Override
    public SymbolPrice getSymbolPrice(String symbolName) {
        return findSymbol(symbolName)
                .map(symbol -> IterableUtils.first(syncRequestClient.getSymbolPriceTicker(symbol)))
                .orElseThrow(() -> new SymbolNotFoundException(symbolName));
    }

    @Override
    public List<SymbolPrice> getAllSymbolPrices() {
        return syncRequestClient.getSymbolPriceTicker("");
    }

    @Override
    public PriceChangeTicker get24hrTickerPriceChange(String symbolName) {
        return IterableUtils.first(syncRequestClient.get24hrTickerPriceChange(symbolName));
    }

    @Override
    public List<PriceChangeTicker> list24hrTickerPriceChange() {
        return syncRequestClient.get24hrTickerPriceChange("");
    }


    private Optional<String> findSymbol(String symbolName) {
        BinanceProperties.Coin coin = binanceProperties.getTetherSymbol().get(symbolName);
        return Optional.ofNullable(coin).map(BinanceProperties.Coin::getName);
    }
}
