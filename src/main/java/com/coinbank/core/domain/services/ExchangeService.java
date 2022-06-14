package com.coinbank.core.domain.services;

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

    void addAssetTicker(User user, String assetName);

    void removeAssetTicker(String assetName);

    List<Asset> listAssets();

    Set<String> availableAssets();

    List<Order> getAllOrders(String assetName, Pageable pageable);

    List<Order> getOpenOrders(String assetName, Pageable pageable);

    Optional<Asset> findAssetByName(User user, String assetName);

    Optional<AssetPrice> getAssetPrice(User user, String assetName);
}
