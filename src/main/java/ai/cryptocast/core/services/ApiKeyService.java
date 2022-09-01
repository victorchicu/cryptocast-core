package ai.cryptocast.core.services;

import ai.cryptocast.core.domain.ApiKey;

import java.security.Principal;
import java.util.List;

public interface ApiKeyService {
    void create(Principal principal, ApiKey apiKey);

    void delete(Principal principal, String label);

    List<ApiKey> list(Principal principal);
}
