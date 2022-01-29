package com.trader.core.repository.converters;

import com.trader.core.domain.AssetChip;
import com.trader.core.entity.AssetChipEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class EntityToAssetChipConverter implements Converter<AssetChipEntity, AssetChip> {
    @Override
    public AssetChip convert(AssetChipEntity source) {
        AssetChip assetChip = new AssetChip();
        assetChip.setAssetName(source.getAssetName());
        return assetChip;
    }
}
