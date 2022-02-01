package com.trader.core.services;

import com.trader.core.binance.domain.account.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.security.Principal;

public interface OrderService {
    void testOrder(Principal principal, String assetName, Order order);

    Page<Order> openOrder(Principal principal, String assetName, Long orderId, Pageable pageable);

    Page<Order> listOrders(Principal principal, String assetName, Pageable pageable);
}
