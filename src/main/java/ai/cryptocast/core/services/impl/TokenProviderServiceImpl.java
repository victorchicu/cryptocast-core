package ai.cryptocast.core.services.impl;

import ai.cryptocast.core.domain.UserPrincipal;
import ai.cryptocast.core.configs.JsonWebTokenConfig;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import ai.cryptocast.core.services.TokenProviderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenProviderServiceImpl implements TokenProviderService {
    private static final Logger LOG = LoggerFactory.getLogger(TokenProviderServiceImpl.class);

    private JsonWebTokenConfig jsonWebTokenConfig;

    public TokenProviderServiceImpl(JsonWebTokenConfig jsonWebTokenConfig) {
        this.jsonWebTokenConfig = jsonWebTokenConfig;
    }

    public String createToken(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Date issuedAt = new Date();
        Date expiresAt = new Date(issuedAt.getTime() + jsonWebTokenConfig.getTokenExpiresInMillis());
        return JWT.create()
                .withSubject(userPrincipal.getId())
                .withIssuedAt(issuedAt)
                .withExpiresAt(expiresAt)
                .sign(Algorithm.HMAC256(jsonWebTokenConfig.getTokenSecret()));
    }

    public String decodeSubject(String token) {
        return JWT.decode(token).getSubject();
    }

    public boolean validateToken(String token) {
        try {
            JWT.decode(token);
            return true;
        } catch (JWTDecodeException ex) {
            LOG.error("Invalid JWT signature: " + ex.getMessage(), ex);
        }
        return false;
    }
}
