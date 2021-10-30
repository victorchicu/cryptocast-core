package com.trader.core.binance.orders.services.impl;

import com.trader.core.binance.configs.BinanceProperties;
import com.trader.core.binance.orders.domain.Order;
import com.trader.core.binance.orders.domain.orders.MarketOrder;
import com.trader.core.binance.orders.services.OrderServiceImpl;

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
