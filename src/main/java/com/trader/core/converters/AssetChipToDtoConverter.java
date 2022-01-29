package com.trader.core.converters;

import com.trader.core.domain.AssetChip;
import com.trader.core.dto.AssetChipDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AssetChipToDtoConverter implements Converter<AssetChip, AssetChipDto> {
    @Override
    public AssetChipDto convert(AssetChip source) {
        return new AssetChipDto(source.getAssetName());
    }
}
