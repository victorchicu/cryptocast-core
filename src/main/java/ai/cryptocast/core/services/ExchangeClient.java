package ai.cryptocast.core.services;

import ai.cryptocast.core.domain.AssetBalance;
import ai.cryptocast.core.domain.AssetPrice;
import ai.cryptocast.core.domain.TestOrder;
import com.binance.api.client.domain.account.Order;
import org.springframework.data.domain.Pageable;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

public interface ExchangeClient {
    void createOrder(Principal principal, String assetName, TestOrder testOrder);

    void cancelOrder(Principal principal, Long orderId, String assetName);

    List<AssetBalance> listAssets();

    List<Order> getAllOrders(String assetName, Pageable pageable);

    List<Order> getOpenOrders(String assetName, Pageable pageable);

    Optional<AssetPrice> getPrice(String assetName);
}
