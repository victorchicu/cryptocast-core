package ai.cryptocast.core.services.impl;

import ai.cryptocast.core.domain.WalletBalance;
import ai.cryptocast.core.domain.AssetPrice;
import ai.cryptocast.core.domain.TestOrder;
import ai.cryptocast.core.domain.User;
import ai.cryptocast.core.services.ExchangeService;
import com.binance.api.client.domain.account.Order;
import org.springframework.data.domain.Pageable;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class GateExchangeService implements ExchangeService {
    @Override
    public void onTickerEvent(Principal principal, Set<String> assetNames) {
        throw new UnsupportedOperationException("onTickerEvent");
    }

    @Override
    public void createOrder(Principal principal, String assetName, TestOrder testOrder) {
        throw new UnsupportedOperationException("createOrder");
    }

    @Override
    public void cancelOrder(Principal principal, Long orderId, String assetName) {
        throw new UnsupportedOperationException("cancelOrder");
    }

    @Override
    public List<WalletBalance> listWalletBalances() {
        throw new UnsupportedOperationException("listAssets");
    }

    @Override
    public List<Order> getAllOrders(String assetName, Pageable pageable) {
        throw new UnsupportedOperationException("getAllOrders");
    }

    @Override
    public List<Order> getOpenOrders(String assetName, Pageable pageable) {
        throw new UnsupportedOperationException("getOpenOrders");
    }

    @Override    // window.location.href = 'https://facebook.com/oauth2/authorization/facebook?redirect_uri=http://localhost:4200/signin';

    public Optional<AssetPrice> getAssetPrice(User user, String assetName) {
        throw new UnsupportedOperationException("getAssetPrice");
    }
}
