package com.trader.core.converters;

import com.trader.core.domain.AssetChip;
import com.trader.core.dto.AssetChipDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DtoToAssetChipConverter implements Converter<AssetChipDto, AssetChip> {
    @Override
    public AssetChip convert(AssetChipDto source) {
        AssetChip assetChip = new AssetChip();
        assetChip.setAssetName(source.getAssetName());
        return assetChip;
    }
}
