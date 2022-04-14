package com.trader.core.converters;

import com.binance.api.client.domain.market.Candlestick;
import com.trader.core.domain.Ohlc;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CandlestickToOhlcConverter implements Converter<Candlestick, Ohlc> {
    @Override
    public Ohlc convert(Candlestick source) {
        Ohlc ohlc = new Ohlc();
        ohlc.setTime(source.getOpenTime());
        ohlc.setOpen(new BigDecimal(source.getOpen()));
        ohlc.setHigh(new BigDecimal(source.getHigh()));
        ohlc.setLow(new BigDecimal(source.getLow()));
        ohlc.setClose(new BigDecimal(source.getClose()));
        return ohlc;
    }
}
