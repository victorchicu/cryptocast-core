package com.coinbank.core.converters;

import com.coinbank.core.domain.AssetTracker;
import com.coinbank.core.dto.AssetTrackerDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SubscriptionToDtoConverter implements Converter<AssetTracker, AssetTrackerDto> {
    @Override
    public AssetTrackerDto convert(AssetTracker source) {
        return new AssetTrackerDto(
                source.getId(),
                source.getAssetName()
        );
    }
}
