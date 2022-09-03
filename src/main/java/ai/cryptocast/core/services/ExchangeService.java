package ai.cryptocast.core.services;

import com.binance.api.client.domain.account.Order;
import ai.cryptocast.core.domain.WalletBalance;
import ai.cryptocast.core.domain.AssetPrice;
import ai.cryptocast.core.domain.TestOrder;
import ai.cryptocast.core.domain.User;
import org.springframework.data.domain.Pageable;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ExchangeService {
    void onTickerEvent(Principal principal, Set<String> assetNames);

    void createOrder(Principal principal, String assetName, TestOrder testOrder);

    void cancelOrder(Principal principal, Long orderId, String assetName);

    List<Order> getAllOrders(String assetName, Pageable pageable);

    List<Order> getOpenOrders(String assetName, Pageable pageable);

    List<WalletBalance> listWalletBalances();

    Optional<AssetPrice> getAssetPrice(User user, String assetName);
}
