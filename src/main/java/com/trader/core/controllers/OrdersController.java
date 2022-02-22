package com.trader.core.controllers;

import com.binance.api.client.domain.account.Order;
import com.trader.core.domain.TestOrder;
import com.trader.core.dto.OrderDto;
import com.trader.core.dto.OrderRequestDto;
import com.trader.core.services.OrderService;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/orders")
public class OrdersController {
    private final OrderService orderService;
    private final ConversionService conversionService;

    public OrdersController(OrderService orderService, ConversionService conversionService) {
        this.orderService = orderService;
        this.conversionService = conversionService;
    }

    @PostMapping("/{assetName}")
    public void createOrder(Principal principal, @PathVariable String assetName, @RequestBody OrderRequestDto orderRequestDto) {
        TestOrder testOrder = toOrder(orderRequestDto);
        orderService.createOrder(principal, assetName, testOrder);
    }

    @GetMapping("/{assetName}")
    public Page<OrderDto> getAllOrders(Principal principal, @PathVariable String assetName, Pageable pageable) {
        return orderService.getAllOrders(principal, assetName, pageable)
                .map(this::toOrderDto);
    }

    @GetMapping("/open")
    public Page<OrderDto> getOpenOrders(Principal principal, @RequestParam String assetName, Pageable pageable) {
        return orderService.getOpenOrders(principal, assetName, pageable)
                .map(this::toOrderDto);
    }

    private TestOrder toOrder(OrderRequestDto orderRequestDto) {
        return conversionService.convert(orderRequestDto, TestOrder.class);
    }

    private OrderDto toOrderDto(Order order) {
        return conversionService.convert(order, OrderDto.class);
    }
}
