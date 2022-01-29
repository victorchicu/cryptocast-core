package com.trader.core.services;

import com.trader.core.binance.domain.account.AssetBalance;
import com.trader.core.clients.ApiRestClient;
import com.trader.core.clients.ApiWebSocketClient;
import com.trader.core.domain.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ExchangeService {
    void createAssetTicker(User user, String assetName);

    void removeAssetTicker(String assetName);

    Set<String> listSymbols();

    ApiRestClient newApiRestClient(User user);

    ApiWebSocketClient newApiWebSocketClient(User user);

    List<AssetBalance> listAssetBalances(User user);

    Optional<AssetBalance> findAssetBalanceByName(User user, String assetName);
}
