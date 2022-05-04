package com.coinbank.core.services.impl;

import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.domain.account.NewOrder;
import com.binance.api.client.domain.account.Order;
import com.binance.api.client.domain.account.request.AllOrdersRequest;
import com.binance.api.client.domain.account.request.CancelOrderRequest;
import com.binance.api.client.domain.market.CandlestickInterval;
import com.binance.api.client.domain.market.TickerPrice;
import com.coinbank.core.configs.BinanceProperties;
import com.coinbank.core.domain.Asset;
import com.coinbank.core.domain.AssetPrice;
import com.coinbank.core.domain.Ohlc;
import com.coinbank.core.domain.TestOrder;
import com.coinbank.core.exceptions.AssetNotFoundException;
import com.coinbank.core.services.ApiRestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Pageable;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ExtendedBinanceApiRestClient implements ApiRestClient {
    private static final Logger LOG = LoggerFactory.getLogger(ExtendedBinanceApiRestClient.class);
    private static final Integer CANDLESTICK_LIMIT = 1000;

    private final ConversionService conversionService;
    private final BinanceProperties binanceProperties;
    private final BinanceApiRestClient binanceApiRestClient;

    public ExtendedBinanceApiRestClient(
            ConversionService conversionService,
            BinanceProperties binanceProperties,
            BinanceApiRestClient binanceApiRestClient
    ) {
        this.conversionService = conversionService;
        this.binanceProperties = binanceProperties;
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
    public List<Ohlc> listOhlc(String assetName, String interval, Long start, Long end) {
        String symbol = toSymbol(assetName);
        return binanceApiRestClient.getCandlestickBars(symbol, CandlestickInterval.valueOf(interval))
                .stream()
                .map(this::toCandlestick)
                .collect(Collectors.toList());
    }

    @Override
    public List<Asset> listAssets() {
        return binanceApiRestClient.getAccount().getBalances()
                .stream()
                .map(this::toAsset)
                .collect(Collectors.toList());
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
    public Optional<AssetPrice> getPrice(String assetName) {
        try {
            String symbol = toSymbol(assetName);
            return Optional.of(binanceApiRestClient.getPrice(symbol))
                    .map(this::toAssetPrice);
        } catch (Exception e) {
            LOG.warn("An error occurred fetching asset " + assetName, e);
            return Optional.empty();
        }
    }


    private Ohlc toCandlestick(com.binance.api.client.domain.market.Candlestick candlestick) {
        return conversionService.convert(candlestick, Ohlc.class);
    }

    private Asset toAsset(com.binance.api.client.domain.account.AssetBalance assetBalance) {
        return conversionService.convert(assetBalance, Asset.class);
    }

    private String toSymbol(String assetName) {
        return Optional.ofNullable(binanceProperties.getAssets().get(assetName))
                .map(BinanceProperties.AssetConfig::getSymbol)
                .orElseThrow(() -> new AssetNotFoundException(assetName));
    }

    private NewOrder toNewOrder(TestOrder testOrder) {
        return conversionService.convert(testOrder, NewOrder.class);
    }

    private AssetPrice toAssetPrice(TickerPrice tickerPrice) {
        return conversionService.convert(tickerPrice, AssetPrice.class);
    }
}
