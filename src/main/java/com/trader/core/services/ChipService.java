package com.trader.core.services;

import com.trader.core.domain.Chip;
import com.trader.core.domain.User;

import java.util.List;

public interface ChipService {
    void removeChip(String assetName, User user);

    Chip addChip(Chip chip);

    List<Chip> listChips(User user);
}
