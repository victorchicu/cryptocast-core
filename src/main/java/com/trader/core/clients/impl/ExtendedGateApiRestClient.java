package com.trader.core.clients.impl;

import com.binance.api.client.domain.account.Order;
import com.binance.api.client.domain.market.TickerPrice;
import com.trader.core.clients.ApiRestClient;
import com.trader.core.domain.AssetBalance;

import java.util.List;

public class ExtendedGateApiRestClient implements ApiRestClient {
    @Override
    public TickerPrice getPrice(String assetName) {
        throw new UnsupportedOperationException("Gate not support get price");
    }

    @Override
    public List<Order> getAllOrders(String assetName) {
        throw new UnsupportedOperationException("Gate not support get all orders");
    }

    @Override
    public List<Order> getOpenOrders(String assetName) {
        throw new UnsupportedOperationException("Gate not support get open orders");
    }

    @Override
    public List<AssetBalance> getAssetsBalances() {
        throw new UnsupportedOperationException("Gate not support get assets balances");
    }
}
