package com.trader.core.services.impl;

import com.trader.core.binance.domain.account.NewOrder;
import com.trader.core.binance.domain.account.Order;
import com.trader.core.binance.domain.account.request.AllOrdersRequest;
import com.trader.core.binance.domain.account.request.OrderStatusRequest;
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
        //TODO: link pageable with AllOrderListRequest
        List<Order> orders = apiRestClient.getAllOrders(new AllOrdersRequest(assetName));
        Order orderStatus = apiRestClient.getOrderStatus(new OrderStatusRequest(assetName, 0L));
        return PageableExecutionUtils.getPage(orders, pageable, () -> orders.size());
    }

    @Override
    public Order testOrder(Principal principal, String assetName, Order order) {
        String symbolName = getSymbolName(assetName);
        apiRestClient.newOrderTest(new NewOrder(order.getSymbol(), order.getSide(), order.getType(), order.getTimeInForce(), order.getOrigQty(), order.getPrice()));
        List<Order> p = apiRestClient.getAllOrders(new AllOrdersRequest(symbolName));
        System.out.println(p);
        return null;
    }

    protected final String getSymbolName(String assetName) {
        return apiRestClient.getSymbolName(assetName);
    }
}
