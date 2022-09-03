package ai.cryptocast.core.services;

import ai.cryptocast.core.domain.Wallet;

public interface ExchangeProvider {
    ExchangeService get(Wallet wallet);
}
