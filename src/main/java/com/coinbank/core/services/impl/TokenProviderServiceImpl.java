package com.coinbank.core.services.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.coinbank.core.domain.UserPrincipal;
import com.coinbank.core.configs.JwtConfig;
import com.coinbank.core.services.TokenProviderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenProviderServiceImpl implements TokenProviderService {
    private static final Logger LOG = LoggerFactory.getLogger(TokenProviderServiceImpl.class);

    private JwtConfig jwtConfig;

    public TokenProviderServiceImpl(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    public String createToken(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Date issuedAt = new Date();
        Date expiresAt = new Date(issuedAt.getTime() + jwtConfig.getTokenExpiresInMillis());
        return JWT.create()
                .withSubject(userPrincipal.getId())
                .withIssuedAt(issuedAt)
                .withExpiresAt(expiresAt)
                .sign(Algorithm.HMAC256(jwtConfig.getTokenSecret()));
    }

    public String decodeSubject(String token) {
        return JWT.decode(token).getSubject();
    }

    public boolean validateToken(String token) {
        try {
            JWT.decode(token);
            return true;
        } catch (JWTDecodeException ex) {
            LOG.error(ex.getMessage(), ex);
            LOG.error("Invalid JWT signature");
        }
        return false;
    }
}
