package com.trader.core.converters;

import com.trader.core.domain.Candlestick;
import com.trader.core.dto.CandlestickDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CandlestickToDtoConverter implements Converter<Candlestick, CandlestickDto> {
    @Override
    public CandlestickDto convert(Candlestick source) {
        return new CandlestickDto(
                source.getOpenTime(),
                source.getOpen(),
                source.getHigh(),
                source.getLow(),
                source.getClose()
        );
    }
}
