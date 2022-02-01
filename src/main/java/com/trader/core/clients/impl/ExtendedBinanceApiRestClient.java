package com.trader.core.clients.impl;

import com.trader.core.binance.BinanceApiRestClient;
import com.trader.core.binance.domain.account.Account;
import com.trader.core.binance.domain.account.Order;
import com.trader.core.binance.domain.account.request.AllOrdersRequest;
import com.trader.core.binance.domain.account.request.OrderStatusRequest;
import com.trader.core.binance.domain.market.TickerPrice;
import com.trader.core.clients.ApiRestClient;
import com.trader.core.configs.BinanceProperties;
import com.trader.core.exceptions.AssetNotFoundException;

import java.util.List;
import java.util.Optional;

public class ExtendedBinanceApiRestClient implements ApiRestClient {
    private final BinanceProperties binanceProperties;
    private final BinanceApiRestClient binanceApiRestClient;

    public ExtendedBinanceApiRestClient(
            BinanceProperties binanceProperties,
            BinanceApiRestClient binanceApiRestClient
    ) {
        this.binanceProperties = binanceProperties;
        this.binanceApiRestClient = binanceApiRestClient;
    }

    @Override
    public Account getAccount() {
        return binanceApiRestClient.getAccount();
    }

    @Override
    public TickerPrice getPrice(String assetName) {
        String symbolName = getSymbolName(assetName);
        return binanceApiRestClient.getPrice(symbolName);
    }

    @Override
    public List<Order> getAllOrders(String assetName) {
        String symbolName = getSymbolName(assetName);
        return binanceApiRestClient.getAllOrders(new AllOrdersRequest(symbolName));
    }

    @Override
    public List<Order> getOpenOrders(String assetName, Long orderId) {
        String symbolName = getSymbolName(assetName);
        return binanceApiRestClient.getOpenOrders(new OrderStatusRequest(symbolName, orderId));
    }


    private String getSymbolName(String assetName) {
        return Optional.ofNullable(binanceProperties.getAssets().get(assetName))
                .map(BinanceProperties.AssetConfig::getSymbol)
                .orElseThrow(AssetNotFoundException::new);
    }
}
