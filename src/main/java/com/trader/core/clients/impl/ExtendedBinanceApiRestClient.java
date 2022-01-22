package com.trader.core.clients.impl;

import com.trader.core.binance.BinanceApiRestClient;
import com.trader.core.binance.domain.account.Account;
import com.trader.core.binance.domain.account.NewOrder;
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
    public void newOrderTest(NewOrder newOrder) {
        binanceApiRestClient.newOrderTest(newOrder);
    }

    @Override
    public Order getOrderStatus(OrderStatusRequest orderStatusRequest) {
        return binanceApiRestClient.getOrderStatus(orderStatusRequest);
    }

    @Override
    public String getSymbolName(String assetName) {
        return Optional.ofNullable(binanceProperties.getAssets().get(assetName))
                .map(asset -> asset.getSymbol())
                .orElseThrow(AssetNotFoundException::new);
    }

    @Override
    public Account getAccount() {
        return binanceApiRestClient.getAccount();
    }

    @Override
    public TickerPrice getPrice(String symbol) {
        return binanceApiRestClient.getPrice(symbol);
    }

    @Override
    public List<Order> getAllOrders(AllOrdersRequest allOrdersRequest) {
        return binanceApiRestClient.getAllOrders(allOrdersRequest);
    }
}
