package com.trader.core.converters;

import com.binance.api.client.domain.TimeInForce;
import com.binance.api.client.domain.account.NewOrder;
import com.trader.core.configs.BinanceProperties;
import com.trader.core.domain.TestOrder;
import com.trader.core.exceptions.AssetNotFoundException;
import com.trader.core.exceptions.IllegalOrderTypeException;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class OrderToNewConverter implements Converter<TestOrder, NewOrder> {
    private final BinanceProperties binanceProperties;

    public OrderToNewConverter(BinanceProperties binanceProperties) {
        this.binanceProperties = binanceProperties;
    }

    @Override
    public NewOrder convert(TestOrder source) {
        String symbol = toSymbol(source.getAsset());
        switch (source.getType()) {
            case LIMIT -> {
                return switch (source.getSide()) {
                    case BUY -> NewOrder.limitBuy(
                            symbol,
                            TimeInForce.GTC,
                            source.getQuantity().toString(),
                            source.getPrice().toString()
                    );
                    case SELL -> NewOrder.limitSell(
                            symbol,
                            TimeInForce.GTC,
                            source.getQuantity().toString(),
                            source.getPrice().toString()
                    );
                };
            }
            case MARKET -> {
                return switch (source.getSide()) {
                    case BUY -> NewOrder.marketBuy(
                            symbol,
                            source.getQuantity().toString()
                    );
                    case SELL -> NewOrder.marketSell(
                            symbol,
                            source.getQuantity().toString()
                    );
                };
            }
            default -> throw new IllegalOrderTypeException(source.getType());
        }
    }

    private String toSymbol(String assetName) {
        return Optional.ofNullable(binanceProperties.getAssets().get(assetName))
                .map(BinanceProperties.AssetConfig::getSymbol)
                .orElseThrow(() -> new AssetNotFoundException(assetName));
    }
}
