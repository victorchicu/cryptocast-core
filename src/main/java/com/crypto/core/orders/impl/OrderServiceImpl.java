package com.crypto.core.orders.impl;

import com.crypto.core.binance.client.BinanceApiRestClient;
import com.crypto.core.binance.client.BinanceApiWebSocketClient;
import com.crypto.core.binance.client.domain.OrderSide;
import com.crypto.core.binance.client.domain.OrderType;
import com.crypto.core.binance.client.domain.TimeInForce;
import com.crypto.core.binance.client.domain.account.NewOrder;
import com.crypto.core.binance.configs.BinanceProperties;
import com.crypto.core.orders.OrderService;
import com.crypto.core.wallet.exceptions.AssetNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    private static final long DEFAULT_RECEIVE_WINDOW = 30000L;

    private final BinanceProperties binanceProperties;
    private final BinanceApiRestClient binanceApiRestClient;
    private final BinanceApiWebSocketClient binanceApiWebSocketClient;

    public OrderServiceImpl(
            BinanceProperties binanceProperties,
            BinanceApiRestClient binanceApiRestClient,
            BinanceApiWebSocketClient binanceApiWebSocketClient
    ) {
        this.binanceProperties = binanceProperties;
        this.binanceApiRestClient = binanceApiRestClient;
        this.binanceApiWebSocketClient = binanceApiWebSocketClient;
    }

    public void createOrder(String assetName) {
        binanceApiRestClient.newOrderTest(new NewOrder(getSymbolName(assetName), OrderSide.SELL, OrderType.MARKET, TimeInForce.FOK, ""));
    }

    private final String getSymbolName(String assetName) {
        return Optional.ofNullable(binanceProperties.getAssets().get(assetName))
                .map(asset -> asset.getCoin())
                .orElseThrow(AssetNotFoundException::new);
    }
}
