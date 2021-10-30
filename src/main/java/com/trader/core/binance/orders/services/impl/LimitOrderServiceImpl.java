package com.trader.core.binance.orders.services.impl;

import com.trader.core.binance.client.BinanceApiRestClient;
import com.trader.core.binance.client.domain.TimeInForce;
import com.trader.core.binance.client.domain.account.NewOrder;
import com.trader.core.binance.client.domain.account.request.AllOrdersRequest;
import com.trader.core.binance.configs.BinanceProperties;
import com.trader.core.binance.orders.domain.Order;
import com.trader.core.binance.orders.domain.orders.LimitOrder;
import com.trader.core.binance.orders.services.OrderServiceImpl;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class LimitOrderServiceImpl extends OrderServiceImpl<LimitOrder> {
    private static final long DEFAULT_RECEIVE_WINDOW = 30000L;

    private final BinanceApiRestClient binanceApiRestClient;

    public LimitOrderServiceImpl(BinanceProperties binanceProperties, BinanceApiRestClient binanceApiRestClient) {
        super(binanceProperties);
        this.binanceApiRestClient = binanceApiRestClient;
    }

    @Override
    public Order testOrder(Principal principal, String assetName, LimitOrder order) {
        String symbolName = getSymbolName(assetName);
        binanceApiRestClient.newOrderTest(NewOrder.limitBuy(symbolName, TimeInForce.GTC, order.getQty().toPlainString(), order.getPrice().toPlainString()));
        List<com.trader.core.binance.client.domain.account.Order> p = binanceApiRestClient.getAllOrders(new AllOrdersRequest(symbolName));
        System.out.println(p);
        return null;
    }
}
