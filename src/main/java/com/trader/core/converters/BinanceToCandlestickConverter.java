package com.trader.core.converters;

import com.trader.core.domain.Candlestick;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class BinanceToCandlestickConverter implements Converter<com.binance.api.client.domain.market.Candlestick, Candlestick> {
    @Override
    public Candlestick convert(com.binance.api.client.domain.market.Candlestick source) {
        Candlestick candlestick = new Candlestick();
        candlestick.setOpenTime(source.getOpenTime());
        candlestick.setOpen(new BigDecimal(source.getOpen()));
        candlestick.setHigh(new BigDecimal(source.getHigh()));
        candlestick.setLow(new BigDecimal(source.getLow()));
        candlestick.setClose(new BigDecimal(source.getClose()));
        return candlestick;
    }
}
