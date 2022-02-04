package com.trader.core.repository.converters;

import com.trader.core.domain.Chip;
import com.trader.core.entity.ChipEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ChipToEntityConverter implements Converter<Chip, ChipEntity> {
    @Override
    public ChipEntity convert(Chip source) {
        ChipEntity chipEntity = new ChipEntity();
        chipEntity.setName(source.getName());
        return chipEntity;
    }
}
