package com.coinbank.core.domain.services;

import com.coinbank.core.domain.ApiKey;

import java.security.Principal;
import java.util.List;

public interface ExchangeConnectService {
    void create(Principal principal, ApiKey apiKey);

    void delete(Principal principal, String label);

    List<ApiKey> list(Principal principal);
}
