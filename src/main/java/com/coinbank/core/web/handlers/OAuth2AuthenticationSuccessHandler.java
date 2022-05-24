package com.coinbank.core.web.handlers;

import com.coinbank.core.domain.exceptions.UnauthorizedRedirectException;
import com.coinbank.core.domain.services.TokenProviderService;
import com.coinbank.core.repository.impl.HttpCookieOAuth2AuthorizationRequestRepository;
import com.coinbank.core.web.configs.OAuth2Properties;
import com.coinbank.core.web.utils.CookieUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static com.coinbank.core.repository.impl.HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME;

@Service
@EnableConfigurationProperties({OAuth2Properties.class})
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private static final Logger LOG = LoggerFactory.getLogger(OAuth2AuthenticationSuccessHandler.class);

    private final OAuth2Properties oAuth2Properties;
    private final TokenProviderService tokenProviderService;
    private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;

    public OAuth2AuthenticationSuccessHandler(
            OAuth2Properties oAuth2Properties,
            TokenProviderService tokenProviderService,
            HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository
    ) {
        this.oAuth2Properties = oAuth2Properties;
        this.tokenProviderService = tokenProviderService;
        this.httpCookieOAuth2AuthorizationRequestRepository = httpCookieOAuth2AuthorizationRequestRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String targetUrl = determineTargetUrl(request, response, authentication);
        if (response.isCommitted()) {
            logger.debug(String.format("Did not redirect to %s since response already committed.", targetUrl));
        } else {
            clearAuthenticationAttributes(request, response);
            getRedirectStrategy().sendRedirect(request, response, targetUrl);
        }
    }

    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Optional<String> redirectUri = CookieUtils.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
                .map(Cookie::getValue);

        if (redirectUri.isPresent() && !isAuthorizedRedirectUri(redirectUri.get())) {
            throw new UnauthorizedRedirectException("Unauthorized redirect URI");
        }

        String encodedUrl = UriUtils.encode(redirectUri.orElse(getDefaultTargetUrl()), StandardCharsets.UTF_8);

        return UriUtils.decode(
                UriComponentsBuilder.fromUriString(encodedUrl)
                        .queryParam("token", tokenProviderService.createToken(authentication))
                        .build(true)
                        .toUriString(),
                StandardCharsets.UTF_8
        );
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }

    private boolean isAuthorizedRedirectUri(String uri) {
        URI clientRedirectUri = URI.create(uri);
        return oAuth2Properties.getAuthorizedRedirectUris().stream()
                .anyMatch(authorizedRedirectUri -> {
                    // Only validate host and port. Let the clients use different paths if they want to
                    URI targetUri = URI.create(authorizedRedirectUri);
                    return targetUri.getHost().equalsIgnoreCase(clientRedirectUri.getHost()) &&
                            targetUri.getPort() == clientRedirectUri.getPort();
                });
    }
}
