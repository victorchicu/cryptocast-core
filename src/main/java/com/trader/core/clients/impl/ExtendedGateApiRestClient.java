package com.trader.core.clients.impl;

import com.binance.api.client.domain.account.Order;
import com.trader.core.clients.ApiRestClient;
import com.trader.core.domain.AssetBalance;
import com.trader.core.domain.AssetPrice;
import com.trader.core.domain.TestOrder;
import org.springframework.data.domain.Pageable;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

public class ExtendedGateApiRestClient implements ApiRestClient {
    @Override
    public void createOrder(Principal principal, String assetName, TestOrder testOrder) {
        throw new UnsupportedOperationException("Gate not support test order");
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

    @Override
    public Optional<AssetPrice> getPrice(String assetName) {
        throw new UnsupportedOperationException("Gate not support get price");
    }
}
