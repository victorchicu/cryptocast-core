package com.trader.core.services;

import org.springframework.security.core.Authentication;

public interface TokenProviderService {
    String createToken(Authentication authentication);

    String decodeSubject(String token);

    boolean validateToken(String token);
}
