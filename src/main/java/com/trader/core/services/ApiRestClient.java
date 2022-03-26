package com.trader.core.services;

import com.binance.api.client.domain.account.Order;
import com.trader.core.domain.AssetBalance;
import com.trader.core.domain.AssetPrice;
import com.trader.core.domain.Candlestick;
import com.trader.core.domain.TestOrder;
import org.springframework.data.domain.Pageable;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

public interface ApiRestClient {
    void createOrder(Principal principal, String assetName, TestOrder testOrder);

    void cancelOrder(Principal principal, Long orderId, String assetName);

    List<Order> getAllOrders(String assetName, Pageable pageable);

    List<Order> getOpenOrders(String assetName, Pageable pageable);

    List<Candlestick> getCandlestick(String assetName, String interval, Long startTime, Long endTime);

    List<AssetBalance> getAssetBalances();

    Optional<AssetPrice> getPrice(String assetName);

}
