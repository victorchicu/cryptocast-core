package com.coinbank.core.services.impl;

import com.binance.api.client.domain.account.Order;
import com.coinbank.core.domain.*;
import com.coinbank.core.services.ExchangeService;
import org.springframework.data.domain.Pageable;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

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
    public List<AssetBalance> listAssetBalances() {
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

    @Override    // window.location.href = 'https://facebook.com/oauth2/authorization/facebook?redirect_uri=http://localhost:4200/signin';

    public Optional<AssetPrice> getAssetPrice(User user, String assetName) {
        throw new UnsupportedOperationException("getAssetPrice");
    }
}
