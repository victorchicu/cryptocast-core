package com.coinbank.core.domain.services.impl;

import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.domain.account.NewOrder;
import com.binance.api.client.domain.account.Order;
import com.binance.api.client.domain.account.request.AllOrdersRequest;
import com.binance.api.client.domain.account.request.CancelOrderRequest;
import com.binance.api.client.domain.event.TickerEvent;
import com.binance.api.client.domain.market.CandlestickInterval;
import com.binance.api.client.domain.market.TickerPrice;
import com.coinbank.core.domain.configs.BinanceConfig;
import com.coinbank.core.domain.configs.BinanceProperties;
import com.coinbank.core.domain.*;
import com.coinbank.core.domain.services.ExchangeService;
import com.coinbank.core.domain.services.NotificationTemplate;
import com.coinbank.core.web.dto.AssetDto;
import com.coinbank.core.enums.NotificationType;
import com.coinbank.core.domain.exceptions.IllegalAssetException;
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

    private final BinanceConfig binanceConfig;
    private final ConversionService conversionService;
    private final NotificationTemplate notificationTemplate;
    private final BinanceApiRestClient binanceApiRestClient;

    public BinanceExchangeService(
            BinanceConfig binanceConfig,
            ConversionService conversionService,
            NotificationTemplate notificationTemplate,
            BinanceApiRestClient binanceApiRestClient
    ) {
        this.binanceConfig = binanceConfig;
        this.conversionService = conversionService;
        this.notificationTemplate = notificationTemplate;
        this.binanceApiRestClient = binanceApiRestClient;
    }

    @Override
    public void createOrder(Principal principal, String assetName, TestOrder testOrder) {
        NewOrder newOrder = toNewOrder(testOrder.setAsset(assetName));
        LOG.info(binanceApiRestClient.newOrder(newOrder).toString());
    }

    @Override
    public void cancelOrder(Principal principal, Long orderId, String assetName) {
        String symbol = toSymbol(assetName);
        binanceApiRestClient.cancelOrder(new CancelOrderRequest(symbol, orderId));
    }

    @Override
    public void addAssetTicker(User user, String assetName) {
//        findAssetByName(user, assetName)
//                .map(asset -> {
//                    events.computeIfAbsent(assetName, (String name) ->
//                            webSocketClient.onTickerEvent(
//                                    asset.getAsset(),
//                                    (TickerEvent tickerEvent) -> {
//                                        try {
//                                            sendNotification(user, asset, tickerEvent);
//                                        } catch (Exception e) {
//                                            LOG.error(e.getMessage(), e);
//                                        }
//                                    }
//                            ));
//                    return asset;
//                })
//                .orElseThrow(() -> new AssetNotFoundException(assetName));
    }

    @Override
    public void removeAssetTicker(String assetName) {
//        Closeable tickerEvent = events.remove(assetName);
//        if (tickerEvent != null) {
//            try {
//                tickerEvent.close();
//            } catch (IOException e) {
//                LOG.error(e.getMessage(), e);
//            }
//        }
    }

    @Override
    public List<Ohlc> listOhlc(String assetName, String interval, Long start, Long end) {
        String symbol = toSymbol(assetName);
        return binanceApiRestClient.getCandlestickBars(symbol, CandlestickInterval.valueOf(interval))
                .stream()
                .map(this::toCandlestick)
                .collect(Collectors.toList());
    }

    @Override
    public List<Asset> listAssets(String label, User user) {
        return binanceApiRestClient.getAccount().getBalances().stream()
                .map(this::toAsset)
                .map(asset -> {
                    asset.setApiKeyName(label);
                    return asset;
                })
                .filter(onlyNonZeroBalance())
                .collect(Collectors.toList());
    }

    @Override
    public Set<String> availableAssets() {
        return binanceConfig.getProps().getAssets().keySet();
    }

    @Override
    public List<Order> getAllOrders(String assetName, Pageable pageable) {
        String symbol = toSymbol(assetName);
        return binanceApiRestClient.getAllOrders(new AllOrdersRequest(symbol));
    }

    @Override
    public List<Order> getOpenOrders(String assetName, Pageable pageable) {
        String symbol = toSymbol(assetName);
        return binanceApiRestClient.getOpenOrders(new AllOrdersRequest(symbol));
    }

    @Override
    public Optional<Asset> findAssetByName(User user, String assetName) {
        throw new UnsupportedOperationException();
//        return exchangeClient.listAssets().stream()
//                .filter(asset -> asset.getAsset().equals(assetName))
//                .map(asset -> {
//                    asset.setPriceChange(BigDecimal.ZERO);
//                    return exchangeClient.getPrice(asset.getAsset())
//                            .map(assetPrice ->
//                                    updateAsset(asset, assetPrice.getPrice())
//                            )
//                            .orElseGet(() ->
//                                    updateAsset(asset, BigDecimal.ONE)
//                            );
//                })
//                .findFirst();
    }

    @Override
    public Optional<AssetPrice> getAssetPrice(User user, String assetName) {
        try {
            String symbolName = toSymbol(assetName);
            return Optional.of(binanceApiRestClient.getPrice(symbolName)).map(this::toAssetPrice);
        } catch (Exception e) {
            LOG.warn("An error occurred fetching asset price " + assetName, e);
            return Optional.empty();
        }
    }

    private void sendNotification(User user, Asset asset, TickerEvent tickerEvent) {
//        asset.setPriceChange(new BigDecimal(tickerEvent.getPriceChangePercent()));
        asset = updateAsset(asset, new BigDecimal(tickerEvent.getBestBidPrice()));
        AssetDto assetDto = toAssetDto(asset);
        notificationTemplate.sendNotification(user, NotificationType.TICKER_EVENT, assetDto);
    }

    private Asset updateAsset(Asset asset, BigDecimal price) {
//        asset.setQuotation(
//                asset.getPrice() == null
//                        ? Quotation.EQUAL
//                        : Quotation.valueOf(price.compareTo(asset.getPrice()))
//        );
//        asset.setPrice(price);
//        BigDecimal balance = BigDecimal.ZERO;
//        if (asset.getFree().compareTo(BigDecimal.ZERO) > 0) {
//            balance = balance.add(
//                    asset.getFree()
//                            .multiply(price)
//                            .setScale(2, RoundingMode.HALF_EVEN)
//            );
//        }
//        if (asset.getLocked().compareTo(BigDecimal.ZERO) > 0) {
//            balance = balance.add(
//                    asset.getLocked()
//                            .multiply(price)
//                            .setScale(2, RoundingMode.HALF_EVEN)
//            );
//        }
//        asset.setBalance(balance);
        return asset;
    }

    private boolean onlyAllowedAssets(Asset asset) {
        return !binanceConfig.getProps().getBlacklist().contains(asset.getName());
    }

    private AssetDto toAssetDto(Asset asset) {
        return conversionService.convert(asset, AssetDto.class);
    }

    private Ohlc toCandlestick(com.binance.api.client.domain.market.Candlestick candlestick) {
        return conversionService.convert(candlestick, Ohlc.class);
    }

    private Asset toAsset(com.binance.api.client.domain.account.AssetBalance assetBalance) {
        return conversionService.convert(assetBalance, Asset.class);
    }

    private String toSymbol(String assetName) {
        return Optional.ofNullable(binanceConfig.getProps().getAssets().get(assetName))
                .map(BinanceProperties.AssetConfig::getSymbol)
                .orElseThrow(() -> new IllegalAssetException(assetName));
    }

    private NewOrder toNewOrder(TestOrder testOrder) {
        return conversionService.convert(testOrder, NewOrder.class);
    }

    private AssetPrice toAssetPrice(TickerPrice tickerPrice) {
        return conversionService.convert(tickerPrice, AssetPrice.class);
    }

    private Predicate<? super Asset> onlyNonZeroBalance() {
        return asset -> asset.getTotalFunds().compareTo(BigDecimal.ZERO) > 0;
    }
}
