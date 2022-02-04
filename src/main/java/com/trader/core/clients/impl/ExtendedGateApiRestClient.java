package com.trader.core.clients.impl;

import com.binance.api.client.domain.account.Order;
import com.binance.api.client.domain.market.TickerPrice;
import com.trader.core.clients.ApiRestClient;
import com.trader.core.domain.FundsBalance;

import java.util.List;

public class ExtendedGateApiRestClient implements ApiRestClient {
    @Override
    public TickerPrice getPrice(String fundsName) {
        throw new UnsupportedOperationException("Gate not support get price");
    }

    @Override
    public List<Order> getAllOrders(String fundsName) {
        throw new UnsupportedOperationException("Gate not support get all orders");
    }

    @Override
    public List<Order> getOpenOrders(String fundsName) {
        throw new UnsupportedOperationException("Gate not support get open orders");
    }

    @Override
    public List<FundsBalance> getFundsBalances() {
        throw new UnsupportedOperationException("Gate not support get funds balances");
    }
}
