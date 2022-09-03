package ai.cryptocast.core.services;

import ai.cryptocast.core.domain.WalletBalance;
import ai.cryptocast.core.domain.User;

import java.util.List;

public interface WalletBalanceService {
    List<WalletBalance> list(User user, String label);
}
