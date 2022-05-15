package com.coinbank.core.domain.services;

import com.coinbank.core.domain.ApiKey;

import java.security.Principal;

public interface ApiConnectionService {
    void create(Principal principal, ApiKey apiKey);

    void delete(Principal principal, String label);
}
