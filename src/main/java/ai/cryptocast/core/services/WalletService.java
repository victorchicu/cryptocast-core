package ai.cryptocast.core.services;

import ai.cryptocast.core.domain.Wallet;

import java.security.Principal;
import java.util.List;

public interface WalletService {
    void create(Principal principal, Wallet wallet);

    void delete(Principal principal, String label);

    List<Wallet> list(Principal principal);
}
