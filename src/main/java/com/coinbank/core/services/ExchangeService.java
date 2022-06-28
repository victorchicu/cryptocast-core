package com.coinbank.core.services;

import com.binance.api.client.domain.account.Order;
import com.coinbank.core.domain.*;
import org.springframework.data.domain.Pageable;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ExchangeService {
    void createOrder(Principal principal, String assetName, TestOrder testOrder);

    void cancelOrder(Principal principal, Long orderId, String assetName);

    List<Order> getAllOrders(String assetName, Pageable pageable);

    List<Order> getOpenOrders(String assetName, Pageable pageable);

    List<AssetBalance> listAssetBalances();

    Optional<AssetPrice> getAssetPrice(User user, String assetName);
}
