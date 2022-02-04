package com.trader.core.services;

import com.trader.core.domain.Chip;
import com.trader.core.domain.User;

import java.util.List;

public interface ChipsService {
    Chip addChip(Chip chip);

    void removeChip(String name, User user);

    List<Chip> listChips(User user);
}
