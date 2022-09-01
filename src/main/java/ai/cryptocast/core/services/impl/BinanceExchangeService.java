package ai.cryptocast.core.services.impl;

import ai.cryptocast.core.domain.AssetBalance;
import ai.cryptocast.core.domain.AssetPrice;
import ai.cryptocast.core.domain.TestOrder;
import ai.cryptocast.core.domain.User;
import ai.cryptocast.core.services.ExchangeService;
import ai.cryptocast.core.services.NotificationTemplate;
import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.BinanceApiWebSocketClient;
import com.binance.api.client.domain.account.NewOrder;
import com.binance.api.client.domain.account.Order;
import com.binance.api.client.domain.account.request.AllOrdersRequest;
import com.binance.api.client.domain.account.request.CancelOrderRequest;
import com.binance.api.client.domain.event.TickerEvent;
import com.binance.api.client.domain.market.TickerPrice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class BinanceExchangeService implements ExchangeService {
    private static final Logger LOG = LoggerFactory.getLogger(BinanceExchangeService.class);

    private final ConversionService conversionService;
    private final NotificationTemplate notificationTemplate;
    private final BinanceApiRestClient binanceApiRestClient;
    private final BinanceApiWebSocketClient binanceApiWebSocketClient;


    public BinanceExchangeService(
            ConversionService conversionService,
            NotificationTemplate notificationTemplate,
            BinanceApiRestClient binanceApiRestClient,
            BinanceApiWebSocketClient binanceApiWebSocketClient
    ) {
        this.conversionService = conversionService;
        this.notificationTemplate = notificationTemplate;
        this.binanceApiRestClient = binanceApiRestClient;
        this.binanceApiWebSocketClient = binanceApiWebSocketClient;
    }

    @Override
    public void onTickerEvent(Principal principal, Set<String> assetNames) {
        binanceApiWebSocketClient.onTickerEvent(
                String.join(",", assetNames),
                (TickerEvent tickerEvent) -> {
                    LOG.trace("{}", tickerEvent);
//                    notificationTemplate.sendNotification();
                });
    }

    @Override
    public void createOrder(Principal principal, String assetName, TestOrder testOrder) {
        NewOrder newOrder = toNewOrder(testOrder.setAsset(assetName));
        LOG.info(binanceApiRestClient.newOrder(newOrder).toString());
    }

    @Override
    public void cancelOrder(Principal principal, Long orderId, String assetName) {
        String symbol = assetName;
        binanceApiRestClient.cancelOrder(new CancelOrderRequest(symbol, orderId));
    }

    @Override
    public List<Order> getAllOrders(String assetName, Pageable pageable) {
        String symbol = assetName;
        return binanceApiRestClient.getAllOrders(new AllOrdersRequest(symbol));
    }

    @Override
    public List<Order> getOpenOrders(String assetName, Pageable pageable) {
        String symbol = assetName;
        return binanceApiRestClient.getOpenOrders(new AllOrdersRequest(symbol));
    }

    @Override
    public List<AssetBalance> listAssetBalances() {
        return binanceApiRestClient.getAccount().getBalances().stream()
                .map(this::toAssetBalance)
                .filter(onlyNonZeroBalance())
                .collect(Collectors.toList());
    }

    @Override
    public Optional<AssetPrice> getAssetPrice(User user, String assetName) {
        try {
            String symbolName = assetName;
            return Optional.of(binanceApiRestClient.getPrice(symbolName)).map(this::toAssetPrice);
        } catch (Exception e) {
            LOG.warn("An error occurred fetching asset price " + assetName, e);
            return Optional.empty();
        }
    }


    private NewOrder toNewOrder(TestOrder testOrder) {
        return conversionService.convert(testOrder, NewOrder.class);
    }

    private AssetPrice toAssetPrice(TickerPrice tickerPrice) {
        return conversionService.convert(tickerPrice, AssetPrice.class);
    }

    private AssetBalance toAssetBalance(com.binance.api.client.domain.account.AssetBalance assetBalance) {
        return conversionService.convert(assetBalance, AssetBalance.class);
    }

    private Predicate<? super AssetBalance> onlyNonZeroBalance() {
        return asset -> asset.getTotalFunds().compareTo(BigDecimal.ZERO) > 0;
    }
}
