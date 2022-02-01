package com.trader.core.controllers;

import com.trader.core.binance.domain.account.Order;
import com.trader.core.services.OrderService;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/orders/{orderId}")
public class OpenOrderController {
    private final OrderService orderService;
    private final ConversionService conversionService;

    public OpenOrderController(OrderService orderService, ConversionService conversionService) {
        this.orderService = orderService;
        this.conversionService = conversionService;
    }

    @GetMapping("/{assetName}")
    public Page<Order> openOrder(Principal principal, @PathVariable Long orderId, @PathVariable String assetName, Pageable pageable) {
        return orderService.openOrder(principal, assetName, orderId, pageable);
    }
}
