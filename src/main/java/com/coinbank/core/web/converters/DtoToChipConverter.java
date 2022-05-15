package com.coinbank.core.web.converters;

import com.coinbank.core.domain.Chip;
import com.coinbank.core.web.dto.ChipDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DtoToChipConverter implements Converter<ChipDto, Chip> {
    @Override
    public Chip convert(ChipDto source) {
        Chip chip = new Chip();
        chip.setName(source.getName());
        return chip;
    }
}
