package com.coinbank.core.services.impl;

import com.binance.api.client.domain.account.Order;
import com.coinbank.core.domain.Asset;
import com.coinbank.core.domain.AssetPrice;
import com.coinbank.core.domain.Ohlc;
import com.coinbank.core.domain.TestOrder;
import com.coinbank.core.services.ApiRestClient;
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
    public List<Ohlc> listOhlc(String assetName, String interval, Long start, Long end) {
        throw new UnsupportedOperationException("listOhlc");
    }

    @Override
    public List<Asset> listAssets() {
        throw new UnsupportedOperationException("listAssets");
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
    public Optional<AssetPrice> getPrice(String assetName) {
        throw new UnsupportedOperationException("getPrice");
    }
}
