package com.trader.core.converters;

import com.trader.core.domain.Chip;
import com.trader.core.dto.ChipDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ChipToDtoConverter implements Converter<Chip, ChipDto> {
    @Override
    public ChipDto convert(Chip source) {
        return new ChipDto(source.getName());
    }
}
