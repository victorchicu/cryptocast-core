package com.crypto.core.wallet.services;

import com.crypto.core.binance.client.domain.wallet.Asset;

import java.security.Principal;
import java.util.List;

public interface WalletService {
    List<Asset> listAssets(Principal principal);
}
