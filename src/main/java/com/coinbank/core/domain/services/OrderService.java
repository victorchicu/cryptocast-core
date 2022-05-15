package com.coinbank.core.domain.services;

import com.binance.api.client.domain.account.Order;
import com.coinbank.core.domain.TestOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.security.Principal;

public interface OrderService {
    void createOrder(Principal principal, String assetName, TestOrder testOrder);

    void cancelOrder(Principal principal, Long orderId, String assetName);

    Page<Order> getAllOrders(Principal principal, String assetName, Pageable pageable);

    Page<Order> getOpenOrders(Principal principal, String assetName, Pageable pageable);
}
