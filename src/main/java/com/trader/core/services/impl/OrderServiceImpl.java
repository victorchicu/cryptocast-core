package com.trader.core.services.impl;

import com.trader.core.exceptions.AssetNotFoundException;
import com.trader.core.binance.BinanceApiRestClient;
import com.trader.core.binance.domain.account.NewOrder;
import com.trader.core.binance.domain.account.Order;
import com.trader.core.binance.domain.account.request.AllOrdersRequest;
import com.trader.core.binance.domain.account.request.OrderStatusRequest;
import com.trader.core.configs.BinanceProperties;
import com.trader.core.services.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    private final BinanceProperties binanceProperties;
    private final BinanceApiRestClient binanceApiRestClient;

    public OrderServiceImpl(BinanceProperties binanceProperties, BinanceApiRestClient binanceApiRestClient) {
        this.binanceProperties = binanceProperties;
        this.binanceApiRestClient = binanceApiRestClient;
    }

    @Override
    public Page<Order> listOrders(Principal principal, String assetName, Pageable pageable) {
        //TODO: link pageable with AllOrderListRequest
        List<Order> orders = binanceApiRestClient.getAllOrders(new AllOrdersRequest(assetName));
        Order orderStatus = binanceApiRestClient.getOrderStatus(new OrderStatusRequest(assetName, 0L));
        return PageableExecutionUtils.getPage(orders, pageable, () -> orders.size());
    }

    @Override
    public Order testOrder(Principal principal, String assetName, Order order) {
        String symbolName = getSymbolName(assetName);
        binanceApiRestClient.newOrderTest(new NewOrder(order.getSymbol(), order.getSide(), order.getType(), order.getTimeInForce(), order.getOrigQty(), order.getPrice()));
        List<Order> p = binanceApiRestClient.getAllOrders(new AllOrdersRequest(symbolName));
        System.out.println(p);
        return null;
    }

    protected final String getSymbolName(String assetName) {
        return Optional.ofNullable(binanceProperties.getAssets().get(assetName))
                .map(asset -> asset.getSymbol())
                .orElseThrow(AssetNotFoundException::new);
    }
}
