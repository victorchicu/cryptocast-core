package com.trader.core.services.impl;

import com.trader.core.binance.domain.account.NewOrder;
import com.trader.core.binance.domain.account.Order;
import com.trader.core.binance.domain.account.request.AllOrdersRequest;
import com.trader.core.clients.ApiRestClient;
import com.trader.core.services.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private final ApiRestClient apiRestClient;

    public OrderServiceImpl(ApiRestClient apiRestClient) {
        this.apiRestClient = apiRestClient;
    }

    @Override
    public Page<Order> listOrders(Principal principal, String assetName, Pageable pageable) {
        String symbol = getSymbolName(assetName);
        List<Order> orders = apiRestClient.getAllOrders(new AllOrdersRequest(symbol));
        return PageableExecutionUtils.getPage(orders, pageable, () -> orders.size());
    }

    @Override
    public void testOrder(Principal principal, String assetName, Order order) {
        String symbolName = getSymbolName(assetName);
        apiRestClient.newOrderTest(
                new NewOrder(
                        symbolName,
                        order.getSide(),
                        order.getType(),
                        order.getTimeInForce(),
                        order.getOrigQty(),
                        order.getPrice()
                )
        );
    }

    protected final String getSymbolName(String assetName) {
        return apiRestClient.getSymbolName(assetName);
    }
}
