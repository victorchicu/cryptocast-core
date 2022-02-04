package com.trader.core.clients;

import com.binance.api.client.domain.account.Order;
import com.binance.api.client.domain.market.TickerPrice;
import com.trader.core.domain.FundsBalance;

import java.util.List;

public interface ApiRestClient {
    TickerPrice getPrice(String fundsName);

    List<Order> getAllOrders(String fundsName);

    List<Order> getOpenOrders(String fundsName);

    List<FundsBalance> getFundsBalances();
}
