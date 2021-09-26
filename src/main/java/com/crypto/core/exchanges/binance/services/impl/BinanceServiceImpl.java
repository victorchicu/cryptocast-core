package com.crypto.core.exchanges.binance.services.impl;

import com.crypto.core.exchanges.binance.client.domain.market.SymbolPrice;
import com.crypto.core.exchanges.binance.client.SubscriptionClient;
import com.crypto.core.exchanges.binance.client.SubscriptionErrorHandler;
import com.crypto.core.exchanges.binance.client.SubscriptionListener;
import com.crypto.core.exchanges.binance.client.SyncRequestClient;
import com.crypto.core.exchanges.binance.client.domain.event.SymbolTickerEvent;
import com.crypto.core.exchanges.binance.configs.BinanceProperties;
import com.crypto.core.exchanges.binance.services.BinanceService;
import com.crypto.core.exchanges.binance.exceptions.SymbolNotFoundException;
import com.crypto.core.exchanges.dto.SymbolDto;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
                SymbolNotFoundException::new
        );
    }

    @Override
    public void unsubscribe(Principal principal, String symbolName) {
        //TODO: unsubscribe by requester id and symbol name
        findSymbol(symbolName).ifPresentOrElse(
                symbol -> subscriptionClient.unsubscribe(principal.getName(), symbol),
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
        return Optional.ofNullable(binanceProperties.getSymbols().get(symbolName))
                .map(BinanceProperties.Symbol::getName);
    }
}
