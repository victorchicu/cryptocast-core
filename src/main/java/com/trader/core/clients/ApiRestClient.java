package com.trader.core.clients;

import com.binance.api.client.domain.account.Order;
import com.trader.core.domain.AssetBalance;
import com.trader.core.domain.AssetPrice;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ApiRestClient {
    List<Order> getAllOrders(String assetName, Pageable pageable);

    List<Order> getOpenOrders(String assetName, Pageable pageable);

    List<AssetBalance> getAssetBalances();

    Optional<AssetPrice> getPrice(String assetName);
}
