package com.crypto.core.auth.services.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.crypto.core.auth.UserPrincipal;
import com.crypto.core.auth.configs.JwtConfig;
import com.crypto.core.auth.services.TokenProviderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenProviderServiceImpl implements TokenProviderService {
    private static final Logger logger = LoggerFactory.getLogger(TokenProviderServiceImpl.class);

    private JwtConfig jwtConfig;

    public TokenProviderServiceImpl(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    public String createToken(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Date now = new Date();
        Date expiresAt = new Date(now.getTime() + jwtConfig.getTokenExpiresInMillis());
        return JWT.create().withSubject(userPrincipal.getId()).withIssuedAt(new Date()).withExpiresAt(expiresAt).sign(Algorithm.HMAC256(jwtConfig.getTokenSecret()));
    }

    public String decodeSubject(String token) {
        return JWT.decode(token).getSubject();
    }

    public boolean validateToken(String token) {
        try {
            JWT.decode(token);
            return true;
        } catch (JWTDecodeException ex) {
            logger.error(ex.getMessage(), ex);
            logger.error("Invalid JWT signature");
        }
        return false;
    }
}
