package ai.cryptocast.core.services;

import org.springframework.cache.annotation.Cacheable;

public interface ExchangeFactory {
    ExchangeService create(String apiKey, String secretKey);
}