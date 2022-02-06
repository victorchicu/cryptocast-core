package com.trader.core.services.impl;

import com.binance.api.client.domain.account.Order;
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
    public void testOrder(Principal principal, String assetName, Order order) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Page<Order> getAllOrders(Principal principal, String assetName, Pageable pageable) {
        List<Order> orders = apiRestClient.getAllOrders(assetName, pageable);
        return PageableExecutionUtils.getPage(orders, pageable, () -> orders.size());
    }

    @Override
    public Page<Order> getOpenOrders(Principal principal, String assetName, Pageable pageable) {
        List<Order> orders = apiRestClient.getOpenOrders(assetName, pageable);
        return PageableExecutionUtils.getPage(orders, pageable, () -> orders.size());
    }
}
