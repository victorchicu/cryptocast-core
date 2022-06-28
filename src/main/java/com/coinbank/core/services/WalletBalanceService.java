package com.coinbank.core.services;

import com.coinbank.core.domain.AssetBalance;
import com.coinbank.core.domain.User;

import java.util.List;

public interface WalletBalanceService {
    List<AssetBalance> list(User user, String label);
}
