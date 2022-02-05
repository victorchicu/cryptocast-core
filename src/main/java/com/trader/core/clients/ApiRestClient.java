package com.trader.core.clients;

import com.binance.api.client.domain.account.Order;
import com.binance.api.client.domain.market.TickerPrice;
import com.trader.core.domain.AssetBalance;

import java.util.List;

public interface ApiRestClient {
    //TODO: Hide Binance details
    TickerPrice getPrice(String assetName);

    List<Order> getAllOrders(String assetName);

    List<Order> getOpenOrders(String assetName);

    List<AssetBalance> getAssetsBalances();
}
