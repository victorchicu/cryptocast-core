package com.trader.core.services.impl;

import com.binance.api.client.domain.account.Order;
import com.trader.core.domain.AssetBalance;
import com.trader.core.domain.AssetPrice;
import com.trader.core.domain.Ohlc;
import com.trader.core.domain.TestOrder;
import com.trader.core.services.ApiRestClient;
import org.springframework.data.domain.Pageable;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

public class ExtendedGateApiRestClient implements ApiRestClient {
    @Override
    public void createOrder(Principal principal, String assetName, TestOrder testOrder) {
        throw new UnsupportedOperationException("createOrder");
    }

    @Override
    public void cancelOrder(Principal principal, Long orderId, String assetName) {
        throw new UnsupportedOperationException("cancelOrder");
    }

    @Override
    public List<Ohlc> listOhlc(String assetName, String interval, Long startTime, Long endTime) {
        throw new UnsupportedOperationException("listOhlc");
    }

    @Override
    public List<Order> getAllOrders(String assetName, Pageable pageable) {
        throw new UnsupportedOperationException("getAllOrders");
    }

    @Override
    public List<Order> getOpenOrders(String assetName, Pageable pageable) {
        throw new UnsupportedOperationException("getOpenOrders");
    }

    @Override
    public List<AssetBalance> getAssetBalances() {
        throw new UnsupportedOperationException("getAssetBalances");
    }

    @Override
    public Optional<AssetPrice> getPrice(String assetName) {
        throw new UnsupportedOperationException("getPrice");
    }
}
