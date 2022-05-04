package com.coinbank.core.converters;

import com.coinbank.core.domain.Ohlc;
import com.coinbank.core.dto.OhlcDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class OhlcToDtoConverter implements Converter<Ohlc, OhlcDto> {
    @Override
    public OhlcDto convert(Ohlc source) {
        return new OhlcDto(
                source.getTime(),
                source.getOpen(),
                source.getHigh(),
                source.getLow(),
                source.getClose()
        );
    }
}
