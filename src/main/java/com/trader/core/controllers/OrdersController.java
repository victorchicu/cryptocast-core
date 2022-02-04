package com.trader.core.controllers;

import com.binance.api.client.domain.account.Order;
import com.trader.core.dto.OrderDto;
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

    @PostMapping("/{fundsName}")
    public OrderDto createOrder(Principal principal, @PathVariable String fundsName, @RequestBody OrderDto orderDto) {
        Order order = toOrder(orderDto);
        //TODO: Remove test order
        orderService.testOrder(principal, fundsName, order);
        return toOrderDto(order);
    }

    @GetMapping("/{fundsName}")
    public Page<OrderDto> getAllOrders(Principal principal, @PathVariable String fundsName, Pageable pageable) {
        return orderService.getAllOrders(principal, fundsName, pageable)
                .map(this::toOrderDto);
    }

    @GetMapping("/open/{fundsName}")
    public Page<OrderDto> getOpenOrders(Principal principal, @PathVariable String fundsName, Pageable pageable) {
        return orderService.getOpenOrders(principal, fundsName, pageable)
                .map(this::toOrderDto);
    }

    private Order toOrder(OrderDto orderDto) {
        return conversionService.convert(orderDto, Order.class);
    }

    private OrderDto toOrderDto(Order order) {
        return conversionService.convert(order, OrderDto.class);
    }
}
