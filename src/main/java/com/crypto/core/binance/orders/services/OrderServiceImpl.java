package com.crypto.core.binance.orders.services;

import com.crypto.core.binance.assets.exceptions.AssetNotFoundException;
import com.crypto.core.binance.configs.BinanceProperties;
import com.crypto.core.binance.orders.domain.Order;
import com.crypto.core.binance.orders.dto.OrderDto;
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
