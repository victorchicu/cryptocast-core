package com.trader.core.converters;

import com.trader.core.domain.Chip;
import com.trader.core.dto.ChipDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DtoToChipConverter implements Converter<ChipDto, Chip> {
    @Override
    public Chip convert(ChipDto source) {
        Chip chip = new Chip();
        chip.setAssetName(source.getName());
        return chip;
    }
}
