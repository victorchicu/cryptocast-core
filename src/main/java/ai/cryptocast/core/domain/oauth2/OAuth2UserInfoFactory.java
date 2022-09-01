package ai.cryptocast.core.domain.oauth2;

import ai.cryptocast.core.domain.exceptions.OAuth2AuthenticationProcessingException;
import ai.cryptocast.core.domain.oauth2.facebook.FacebookOAuth2UserInfo;
import ai.cryptocast.core.domain.oauth2.github.GithubOAuth2UserInfo;
import ai.cryptocast.core.domain.oauth2.google.GoogleOAuth2UserInfo;
import ai.cryptocast.core.enums.OAuth2Provider;

import java.util.Map;

public class OAuth2UserInfoFactory {
    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        //TODO: refactor to Spring factory
        if (registrationId.equalsIgnoreCase(OAuth2Provider.GOOGLE.toString())) {
            return new GoogleOAuth2UserInfo(attributes);
        } else if (registrationId.equalsIgnoreCase(OAuth2Provider.GITHUB.toString())) {
            return new GithubOAuth2UserInfo(attributes);
        } else if (registrationId.equalsIgnoreCase(OAuth2Provider.FACEBOOK.toString())) {
            return new FacebookOAuth2UserInfo(attributes);
        } else {
            throw new OAuth2AuthenticationProcessingException(registrationId + " is not supported yet.");
        }
    }
}
