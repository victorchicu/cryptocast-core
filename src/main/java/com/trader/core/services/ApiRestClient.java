package com.trader.core.services;

import com.binance.api.client.domain.account.Order;
import com.trader.core.domain.Asset;
import com.trader.core.domain.AssetPrice;
import com.trader.core.domain.Ohlc;
import com.trader.core.domain.TestOrder;
import org.springframework.data.domain.Pageable;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

public interface ApiRestClient {
    void createOrder(Principal principal, String assetName, TestOrder testOrder);

    void cancelOrder(Principal principal, Long orderId, String assetName);

    List<Ohlc> listOhlc(String assetName, String interval, Long start, Long end);

    List<Asset> listAssets();

    List<Order> getAllOrders(String assetName, Pageable pageable);

    List<Order> getOpenOrders(String assetName, Pageable pageable);

    Optional<AssetPrice> getPrice(String assetName);
}
