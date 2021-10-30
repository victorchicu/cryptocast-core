package com.crypto.core.binance.orders.services.impl;

import com.crypto.core.binance.configs.BinanceProperties;
import com.crypto.core.binance.orders.domain.Order;
import com.crypto.core.binance.orders.domain.orders.MarketOrder;
import com.crypto.core.binance.orders.services.OrderServiceImpl;

import java.security.Principal;

public class MarkerOrderServiceImpl extends OrderServiceImpl<MarketOrder> {
    public MarkerOrderServiceImpl(BinanceProperties binanceProperties) {
        super(binanceProperties);
    }

    @Override
    public Order testOrder(Principal principal, String assetName, MarketOrder order) {
        return null;
    }
}
