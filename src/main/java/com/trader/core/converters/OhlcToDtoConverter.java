package com.trader.core.converters;

import com.trader.core.domain.Ohlc;
import com.trader.core.dto.OhlcDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class OhlcToDtoConverter implements Converter<Ohlc, OhlcDto> {
    @Override
    public OhlcDto convert(Ohlc source) {
        return new OhlcDto(
                source.getOpenTime(),
                source.getOpen(),
                source.getHigh(),
                source.getLow(),
                source.getClose()
        );
    }
}
