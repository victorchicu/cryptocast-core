package com.trader.core.services;

import com.binance.api.client.domain.account.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.security.Principal;

public interface OrderService {
    void testOrder(Principal principal, String fundsName, Order order);

    Page<Order> openOrder(Principal principal, String fundsName, Pageable pageable);

    Page<Order> listOrders(Principal principal, String fundsName, Pageable pageable);
}
