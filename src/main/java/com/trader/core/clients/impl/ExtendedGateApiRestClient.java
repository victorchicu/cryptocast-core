package com.trader.core.clients.impl;

import com.binance.api.client.domain.account.Order;
import com.binance.api.client.domain.market.TickerPrice;
import com.trader.core.clients.ApiRestClient;
import com.trader.core.domain.AssetBalance;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class ExtendedGateApiRestClient implements ApiRestClient {
    @Override
    public TickerPrice getPrice(String assetName) {
        throw new UnsupportedOperationException("Gate not support get price");
    }

    @Override
    public List<Order> getAllOrders(String assetName, Pageable pageable) {
        throw new UnsupportedOperationException("Gate not support get all orders");
    }

    @Override
    public List<Order> getOpenOrders(String assetName, Pageable pageable) {
        throw new UnsupportedOperationException("Gate not support get open orders");
    }

    @Override
    public List<AssetBalance> getAssetBalances() {
        throw new UnsupportedOperationException("Gate not support get assets balances");
    }
}
