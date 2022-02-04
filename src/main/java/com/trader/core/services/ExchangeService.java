package com.trader.core.services;

import com.trader.core.clients.ApiRestClient;
import com.trader.core.clients.ApiWebSocketClient;
import com.trader.core.domain.FundsBalance;
import com.trader.core.domain.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ExchangeService {
    void createFundsTicker(User user, String fundsName);

    void removeFundsTicker(String fundsName);

    Set<String> listSymbols();

    ApiRestClient newApiRestClient(User user);

    ApiWebSocketClient newApiWebSocketClient(User user);

    List<FundsBalance> listFundsBalances(User user);

    Optional<FundsBalance> findFundsByName(User user, String fundsName);
}
