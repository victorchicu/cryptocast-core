package com.crypto.core.auth.services;

import org.springframework.security.core.Authentication;

public interface TokenProviderService {
    String createToken(Authentication authentication);

    String decodeSubject(String token);

    boolean validateToken(String token);
}
