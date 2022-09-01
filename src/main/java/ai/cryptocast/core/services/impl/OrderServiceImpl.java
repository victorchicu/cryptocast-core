package ai.cryptocast.core.services.impl;

import ai.cryptocast.core.domain.TestOrder;
import com.binance.api.client.domain.account.Order;
import ai.cryptocast.core.services.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class OrderServiceImpl implements OrderService {
//    private final ExchangeClient exchangeClient;

//    public OrderServiceImpl(ExchangeClient exchangeClient) {
//        this.exchangeClient = exchangeClient;
//    }

    @Override
    public void createOrder(Principal principal, String assetName, TestOrder testOrder) {
//        exchangeClient.createOrder(principal, assetName, testOrder);
    }

    @Override
    public void cancelOrder(Principal principal, Long orderId, String assetName) {
//        exchangeClient.cancelOrder(principal, orderId, assetName);
    }

    @Override
    public Page<Order> getAllOrders(Principal principal, String assetName, Pageable pageable) {
        throw new UnsupportedOperationException();
//        List<Order> orders = exchangeClient.getAllOrders(assetName, pageable);
//        return PageableExecutionUtils.getPage(orders, pageable, () -> orders.size());
    }

    @Override
    public Page<Order> getOpenOrders(Principal principal, String assetName, Pageable pageable) {
        throw new UnsupportedOperationException();
//        List<Order> orders = exchangeClient.getOpenOrders(assetName, pageable);
//        return PageableExecutionUtils.getPage(orders, pageable, () -> orders.size());
    }
}
