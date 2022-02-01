package com.trader.core.clients.impl;

import com.trader.core.binance.domain.account.Account;
import com.trader.core.binance.domain.account.Order;
import com.trader.core.binance.domain.market.TickerPrice;
import com.trader.core.clients.ApiRestClient;

import java.util.List;

public class ExtendedGateApiRestClient implements ApiRestClient {
    @Override
    public Account getAccount() {
        throw new UnsupportedOperationException("Gate not support get account");
    }

    @Override
    public TickerPrice getPrice(String assetName) {
        throw new UnsupportedOperationException("Gate not support get price");
    }

    @Override
    public List<Order> getAllOrders(String assetName) {
        throw new UnsupportedOperationException("Gate not support get all orders");
    }

    @Override
    public List<Order> getOpenOrders(String assetName, Long orderId) {
        throw new UnsupportedOperationException("Gate not support get open orders");
    }
}
