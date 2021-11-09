package com.trader.core.binance.orders.services;

import com.trader.core.binance.orders.domain.Order;
import org.springframework.data.domain.Page;

import java.security.Principal;

public interface OrderService<T extends Order> {
    Order testOrder(Principal principal, String assetName, T order);

    Page<T> listOrders(Principal principal);
}
