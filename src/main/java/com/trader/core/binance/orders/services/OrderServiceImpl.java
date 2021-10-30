package com.trader.core.binance.orders.services;

import com.trader.core.binance.assets.exceptions.AssetNotFoundException;
import com.trader.core.binance.configs.BinanceProperties;
import com.trader.core.binance.orders.domain.Order;
import com.trader.core.binance.orders.dto.OrderDto;
import org.springframework.data.domain.Page;

import java.security.Principal;
import java.util.Optional;

public abstract class OrderServiceImpl<T extends Order> implements OrderService<T> {
    private final BinanceProperties binanceProperties;

    public OrderServiceImpl(BinanceProperties binanceProperties) {
        this.binanceProperties = binanceProperties;
    }

    @Override
    public Page<OrderDto> listOrders(Principal principal) {
        return null;
    }

    protected final String getSymbolName(String assetName) {
        return Optional.ofNullable(binanceProperties.getAssets().get(assetName))
                .map(asset -> asset.getCoin())
                .orElseThrow(AssetNotFoundException::new);
    }
}
