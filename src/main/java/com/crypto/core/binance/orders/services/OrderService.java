package com.crypto.core.binance.orders.services;

import com.crypto.core.binance.orders.domain.Order;
import com.crypto.core.binance.orders.dto.OrderDto;
import org.springframework.data.domain.Page;

import java.security.Principal;

public interface OrderService<T extends Order> {
    Order testOrder(Principal principal, String assetName, T order);

    Page<OrderDto> listOrders(Principal principal);
}
