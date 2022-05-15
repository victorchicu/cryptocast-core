package com.coinbank.core.domain.converters;

import com.binance.api.client.domain.market.TickerPrice;
import com.coinbank.core.domain.AssetPrice;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class TickerPriceToAssetPriceConverter implements Converter<TickerPrice, AssetPrice> {
    @Override
    public AssetPrice convert(TickerPrice source) {
        AssetPrice assetPrice = new AssetPrice();
        assetPrice.setSymbol(source.getSymbol());
        assetPrice.setPrice(new BigDecimal(source.getPrice()));
        return assetPrice;
    }
}
