package com.trader.core.clients;

import com.trader.core.binance.domain.account.Account;
import com.trader.core.binance.domain.account.Order;
import com.trader.core.binance.domain.market.TickerPrice;

import java.util.List;

public interface ApiRestClient {
    Account getAccount();

    TickerPrice getPrice(String assetName);

    List<Order> getAllOrders(String assetName);

    List<Order> getOpenOrders(String assetName, Long orderId);
}
