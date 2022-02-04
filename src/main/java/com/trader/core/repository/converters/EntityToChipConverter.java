package com.trader.core.repository.converters;

import com.trader.core.domain.Chip;
import com.trader.core.entity.ChipEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class EntityToChipConverter implements Converter<ChipEntity, Chip> {
    @Override
    public Chip convert(ChipEntity source) {
        Chip chip = new Chip();
        chip.setName(source.getName());
        return chip;
    }
}
