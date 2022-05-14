package com.coinbank.core.services.impl;

import com.binance.api.client.domain.account.Order;
import com.coinbank.core.domain.*;
import com.coinbank.core.services.ExchangeService;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class GateExchangeService implements ExchangeService {
    @Override
    public void createOrder(Principal principal, String assetName, TestOrder testOrder) {
        throw new UnsupportedOperationException("createOrder");
    }

    @Override
    public void cancelOrder(Principal principal, Long orderId, String assetName) {
        throw new UnsupportedOperationException("cancelOrder");
    }

    @Override
    public void addAssetTicker(User user, String assetName) {
        throw new UnsupportedOperationException("createAssetTicker");
    }

    @Override
    public void removeAssetTicker(String assetName) {
        throw new UnsupportedOperationException("removeAssetTicker");
    }

    @Override
    public List<Ohlc> listOhlc(String assetName, String interval, Long start, Long end) {
        throw new UnsupportedOperationException("listOhlc");
    }

    @Override
    public List<Asset> listAssets(String label, User user) {
        throw new UnsupportedOperationException("listAssets");
    }

    @Override
    public Set<String> availableAssets() {
        throw new UnsupportedOperationException("availableAssets");
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
    public Optional<Asset> findAssetByName(User user, String assetName) {
        throw new UnsupportedOperationException("findAssetByName");
    }

    @Override
    public Optional<AssetPrice> getAssetPrice(User user, String assetName) {
        throw new UnsupportedOperationException("getAssetPrice");
    }

    @Override
    public void close() throws IOException {

    }
}
