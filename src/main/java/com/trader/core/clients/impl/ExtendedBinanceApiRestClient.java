package com.trader.core.clients.impl;

import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.domain.account.NewOrder;
import com.binance.api.client.domain.account.Order;
import com.binance.api.client.domain.account.request.AllOrdersRequest;
import com.binance.api.client.domain.market.TickerPrice;
import com.trader.core.clients.ApiRestClient;
import com.trader.core.configs.BinanceProperties;
import com.trader.core.domain.AssetBalance;
import com.trader.core.domain.AssetPrice;
import com.trader.core.domain.TestOrder;
import com.trader.core.exceptions.AssetNotFoundException;
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

    private final BinanceProperties binanceProperties;
    private final BinanceApiRestClient binanceApiRestClient;
    private final ConversionService conversionService;

    public ExtendedBinanceApiRestClient(
            BinanceProperties binanceProperties,
            BinanceApiRestClient binanceApiRestClient,
            ConversionService conversionService
    ) {
        this.binanceProperties = binanceProperties;
        this.binanceApiRestClient = binanceApiRestClient;
        this.conversionService = conversionService;
    }

    @Override
    public void createOrder(Principal principal, String assetName, TestOrder testOrder) {
        NewOrder newOrder = toNewOrder(testOrder.setAsset(assetName));
        LOG.info(binanceApiRestClient.newOrder(newOrder).toString());
    }


    @Override
    public List<Order> getAllOrders(String assetName, Pageable pageable) {
        String symbol = toSymbol(assetName);
        return binanceApiRestClient.getAllOrders(new AllOrdersRequest(symbol));
    }

    @Override
    public List<Order> getOpenOrders(String assetName, Pageable pageable) {
        String symbol = toSymbol(assetName);
        List<Order> p = binanceApiRestClient.getOpenOrders(new AllOrdersRequest(symbol));
        System.out.println(p);
        return p;
    }

    @Override
    public List<AssetBalance> getAssetBalances() {
        return binanceApiRestClient.getAccount().getBalances()
                .stream()
                .map(this::toAssetBalance)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<AssetPrice> getPrice(String assetName) {
        try {
            String symbol = toSymbol(assetName);
            return Optional.of(binanceApiRestClient.getPrice(symbol)).map(this::toAssetPrice);
        } catch (Exception e) {
            LOG.error("An error occurred fetching asset price: " + e.getMessage(), e);
            return Optional.empty();
        }
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

    private AssetBalance toAssetBalance(com.binance.api.client.domain.account.AssetBalance assetBalance) {
        return conversionService.convert(assetBalance, AssetBalance.class);
    }
}
