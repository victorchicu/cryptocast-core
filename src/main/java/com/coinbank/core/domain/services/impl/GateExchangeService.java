package com.coinbank.core.domain.services.impl;

import com.binance.api.client.domain.account.Order;
import com.coinbank.core.domain.*;
import com.coinbank.core.domain.services.ExchangeService;
import org.springframework.data.domain.Pageable;

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
    public List<Asset> listAssets() {
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

    @Override    // window.location.href = 'https://facebook.com/oauth2/authorization/facebook?redirect_uri=http://localhost:4200/signin';

    public Optional<AssetPrice> getAssetPrice(User user, String assetName) {
        throw new UnsupportedOperationException("getAssetPrice");
    }
}
