package com.trader.core.orders.services;

import com.trader.core.binance.client.domain.account.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.security.Principal;

public interface OrderService {
    Order testOrder(Principal principal, String assetName, Order order);

    Page<Order> listOrders(Principal principal, String assetName, Pageable pageable);
}
