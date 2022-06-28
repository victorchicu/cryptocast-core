package com.coinbank.core.services;

import com.binance.api.client.domain.account.Order;
import com.coinbank.core.domain.AssetBalance;
import com.coinbank.core.domain.AssetPrice;
import com.coinbank.core.domain.TestOrder;
import org.springframework.data.domain.Pageable;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

public interface ExchangeClient {
    void createOrder(Principal principal, String assetName, TestOrder testOrder);

    void cancelOrder(Principal principal, Long orderId, String assetName);

    List<AssetBalance> listAssets();

    List<Order> getAllOrders(String assetName, Pageable pageable);

    List<Order> getOpenOrders(String assetName, Pageable pageable);

    Optional<AssetPrice> getPrice(String assetName);
}
