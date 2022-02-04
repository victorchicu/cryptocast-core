package com.trader.core.services;

import com.trader.core.domain.FundsBalance;
import com.trader.core.domain.User;

import java.util.List;

public interface FundsService {
    void addFundsTickerEvent(User user, String fundsName);

    void removeFundsTickerEvent(User user, String fundsName);

    List<FundsBalance> listFundsBalances(User user);
}
