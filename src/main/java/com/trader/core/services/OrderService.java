package com.trader.core.services;

import com.binance.api.client.domain.account.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.security.Principal;

public interface OrderService {
    void testOrder(Principal principal, String assetName, Order order);

    Page<Order> getAllOrders(Principal principal, String assetName, Pageable pageable);

    Page<Order> getOpenOrders(Principal principal, String assetName, Pageable pageable);
}
