package com.trader.core.controllers;

import com.trader.core.binance.domain.account.Order;
import com.trader.core.dto.OrderDto;
import com.trader.core.services.OrderService;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;
    private final ConversionService conversionService;

    public OrderController(OrderService orderService, ConversionService conversionService) {
        this.orderService = orderService;
        this.conversionService = conversionService;
    }

    @PostMapping("/{assetName}")
    public OrderDto createOrder(Principal principal, @PathVariable String assetName, @RequestBody OrderDto orderDto) {
        Order order = toOrder(orderDto);
        //TODO: Remove test order
        orderService.testOrder(principal, assetName, order);
        return toOrderDto(order);
    }

    @GetMapping
    public Page<OrderDto> listOrders(Principal principal, @RequestParam String assetName, Pageable pageable) {
        return orderService.listOrders(principal, assetName, pageable)
                .map(this::toOrderDto);
    }




    private Order toOrder(OrderDto orderDto) {
        return conversionService.convert(orderDto, Order.class);
    }

    private OrderDto toOrderDto(Order order) {
        return conversionService.convert(order, OrderDto.class);
    }
}
