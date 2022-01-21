package com.trader.core.oauth2;

import com.trader.core.enums.OAuth2Provider;
import com.trader.core.oauth2.exceptions.OAuth2AuthenticationProcessingException;
import com.trader.core.oauth2.facebook.FacebookOAuth2UserInfo;
import com.trader.core.oauth2.github.GithubOAuth2UserInfo;
import com.trader.core.oauth2.google.GoogleOAuth2UserInfo;

import java.util.Map;

public class OAuth2UserInfoFactory {
    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        if (registrationId.equalsIgnoreCase(OAuth2Provider.GOOGLE.toString())) {
            return new GoogleOAuth2UserInfo(attributes);
        } else if (registrationId.equalsIgnoreCase(OAuth2Provider.FACEBOOK.toString())) {
            return new FacebookOAuth2UserInfo(attributes);
        } else if (registrationId.equalsIgnoreCase(OAuth2Provider.GITHUB.toString())) {
            return new GithubOAuth2UserInfo(attributes);
        } else {
            throw new OAuth2AuthenticationProcessingException("Sorry! Login with " + registrationId + " is not supported yet.");
        }
    }
}
