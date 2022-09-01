package ai.cryptocast.core.services;

import ai.cryptocast.core.domain.AssetBalance;
import ai.cryptocast.core.domain.User;

import java.util.List;

public interface WalletBalanceService {
    List<AssetBalance> list(User user, String label);
}
