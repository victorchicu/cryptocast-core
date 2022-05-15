package com.coinbank.core.domain.services;

import com.coinbank.core.domain.Chip;
import com.coinbank.core.domain.User;

import java.util.List;

public interface ChipsService {
    Chip addChip(Chip chip);

    void removeChip(String name, User user);

    List<Chip> listChips(User user);
}
