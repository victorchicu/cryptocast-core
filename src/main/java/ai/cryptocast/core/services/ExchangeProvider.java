package ai.cryptocast.core.services;

import ai.cryptocast.core.domain.ApiKey;

public interface ExchangeProvider {
    ExchangeService get(ApiKey apiKey);
}
