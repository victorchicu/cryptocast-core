package com.trader.core.clients;

import com.trader.core.binance.domain.account.Account;
import com.trader.core.binance.domain.account.NewOrder;
import com.trader.core.binance.domain.account.Order;
import com.trader.core.binance.domain.account.request.AllOrdersRequest;
import com.trader.core.binance.domain.account.request.OrderStatusRequest;
import com.trader.core.binance.domain.market.TickerPrice;

import java.util.List;

public interface ApiRestClient {
    void newOrderTest(NewOrder newOrder);

    Order getOrderStatus(OrderStatusRequest orderStatusRequest);

    String getSymbolName(String assetName);

    Account getAccount();

    TickerPrice getPrice(String symbol);

    List<Order> getAllOrders(AllOrdersRequest allOrdersRequest);
}
