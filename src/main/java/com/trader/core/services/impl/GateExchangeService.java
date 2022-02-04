package com.trader.core.services.impl;

import com.trader.core.clients.ApiRestClient;
import com.trader.core.clients.ApiWebSocketClient;
import com.trader.core.domain.FundsBalance;
import com.trader.core.domain.User;
import com.trader.core.services.ExchangeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service("GATE")
public class GateExchangeService implements ExchangeService {
    @Override
    public void createFundsTicker(User user, String fundsName) {
        throw new UnsupportedOperationException("Gate not support create asset ticker");
    }

    @Override
    public void removeFundsTicker(String fundsName) {
        throw new UnsupportedOperationException("Gate not support remove asset ticker");
    }

    @Override
    public Set<String> listSymbols() {
        throw new UnsupportedOperationException("Gate not support list symbols");
    }

    @Override
    public ApiRestClient newApiRestClient(User user) {
        throw new UnsupportedOperationException("Gate not support api rest client");
    }

    @Override
    public ApiWebSocketClient newApiWebSocketClient(User user) {
        throw new UnsupportedOperationException("Gate not support api web socket client");
    }

    @Override
    public List<FundsBalance> listFundsBalances(User user) {
        throw new UnsupportedOperationException("Gate not support list asset balances");
    }

    @Override
    public Optional<FundsBalance> findFundsByName(User user, String fundsName) {
        throw new UnsupportedOperationException("Gate not support find asset balance by name");
    }
}
