package com.trader.core.binance.orders.controllers;

import com.trader.core.binance.orders.domain.Order;
import com.trader.core.binance.orders.dto.OrderDto;
import com.trader.core.binance.orders.services.OrderService;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.util.CastUtils;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final ConversionService conversionService;
    private final OrderService<? extends Order> orderService;

    public OrderController(ConversionService conversionService, OrderService<? extends Order> orderService) {
        this.conversionService = conversionService;
        this.orderService = orderService;
    }

    @PostMapping("/{assetName}")
    public OrderDto createOrder(Principal principal, @PathVariable String assetName, @RequestBody OrderDto orderDto) {
        Order order = toOrder(orderDto);
        return toOrderDto(orderService.testOrder(principal, assetName, CastUtils.cast(order)));
    }

    @GetMapping
    public Page<? extends OrderDto> listOrders(Principal principal) {
        return orderService.listOrders(principal)
                .map(this::toOrderDto);
    }

    private Order toOrder(OrderDto orderDto) {
        return conversionService.convert(orderDto, Order.class);
    }

    private OrderDto toOrderDto(Order order) {
        return conversionService.convert(order, OrderDto.class);
    }
}
