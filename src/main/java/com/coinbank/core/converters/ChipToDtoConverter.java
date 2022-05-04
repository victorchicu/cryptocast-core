package com.coinbank.core.converters;

import com.coinbank.core.domain.Chip;
import com.coinbank.core.dto.ChipDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ChipToDtoConverter implements Converter<Chip, ChipDto> {
    @Override
    public ChipDto convert(Chip source) {
        return new ChipDto(source.getName());
    }
}
