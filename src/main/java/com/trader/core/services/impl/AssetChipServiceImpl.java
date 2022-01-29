package com.trader.core.services.impl;

import com.trader.core.domain.AssetChip;
import com.trader.core.domain.User;
import com.trader.core.entity.AssetChipEntity;
import com.trader.core.repository.AssetChipRepository;
import com.trader.core.services.AssetChipService;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AssetChipServiceImpl implements AssetChipService {
    private final ConversionService conversionService;
    private final AssetChipRepository assetChipRepository;

    public AssetChipServiceImpl(
            ConversionService conversionService,
            AssetChipRepository assetChipRepository
    ) {
        this.conversionService = conversionService;
        this.assetChipRepository = assetChipRepository;
    }

    @Override
    public void removeAssetChip(String assetName, User user) {
        assetChipRepository.deleteByAssetNameAndCreatedBy(assetName, user.getId());
    }

    @Override
    public AssetChip addAssetChip(AssetChip assetChip) {
        AssetChipEntity assetChipEntity = toAssetChipEntity(assetChip);
        assetChipEntity = assetChipRepository.save(assetChipEntity);
        return toAssetChip(assetChipEntity);
    }

    @Override
    public List<AssetChip> listAssetChips(User user) {
        return assetChipRepository.findAllByCreatedBy(user.getId()).stream()
                .map(this::toAssetChip)
                .collect(Collectors.toList());
    }


    private AssetChip toAssetChip(AssetChipEntity assetChipEntity) {
        return conversionService.convert(assetChipEntity, AssetChip.class);
    }

    private AssetChipEntity toAssetChipEntity(AssetChip assetChip) {
        return conversionService.convert(assetChip, AssetChipEntity.class);
    }
}
