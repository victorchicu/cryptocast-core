package com.trader.core.clients.impl;

import com.trader.core.binance.domain.account.Account;
import com.trader.core.binance.domain.account.NewOrder;
import com.trader.core.binance.domain.account.Order;
import com.trader.core.binance.domain.account.request.AllOrdersRequest;
import com.trader.core.binance.domain.account.request.OrderStatusRequest;
import com.trader.core.binance.domain.market.TickerPrice;
import com.trader.core.clients.ApiRestClient;

import java.util.List;

public class ExtendedGateApiRestClient implements ApiRestClient {
    @Override
    public void newOrderTest(NewOrder newOrder) {
        throw new UnsupportedOperationException("Gate not support new order test");
    }

    @Override
    public Order getOrderStatus(OrderStatusRequest orderStatusRequest) {
        throw new UnsupportedOperationException("Gate not support get order status");
    }

    @Override
    public String getSymbolName(String assetName) {
        throw new UnsupportedOperationException("Gate not support get symbol name");
    }

    @Override
    public Account getAccount() {
        throw new UnsupportedOperationException("Gate not support get account");
    }

    @Override
    public TickerPrice getPrice(String symbol) {
        throw new UnsupportedOperationException("Gate not support get price");
    }

    @Override
    public List<Order> getAllOrders(AllOrdersRequest allOrdersRequest) {
        throw new UnsupportedOperationException("Gate not support get all orders");
    }
}
