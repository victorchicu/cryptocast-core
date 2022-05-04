package com.coinbank.core.services.impl;

import com.coinbank.core.domain.Chip;
import com.coinbank.core.domain.User;
import com.coinbank.core.entity.ChipEntity;
import com.coinbank.core.repository.ChipRepository;
import com.coinbank.core.services.ChipsService;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChipsServiceImpl implements ChipsService {
    private final ChipRepository chipRepository;
    private final ConversionService conversionService;

    public ChipsServiceImpl(ChipRepository chipRepository, ConversionService conversionService) {
        this.chipRepository = chipRepository;
        this.conversionService = conversionService;
    }

    @Override
    public Chip addChip(Chip chip) {
        ChipEntity chipEntity = toChipEntity(chip);
        chipEntity = chipRepository.save(chipEntity);
        return toChip(chipEntity);
    }

    @Override
    public void removeChip(String name, User user) {
        chipRepository.deleteByNameAndCreatedBy(name, user.getId());
    }

    @Override
    public List<Chip> listChips(User user) {
        return chipRepository.findAllByCreatedBy(user.getId()).stream()
                .map(this::toChip)
                .collect(Collectors.toList());
    }


    private Chip toChip(ChipEntity chipEntity) {
        return conversionService.convert(chipEntity, Chip.class);
    }

    private ChipEntity toChipEntity(Chip chip) {
        return conversionService.convert(chip, ChipEntity.class);
    }
}
