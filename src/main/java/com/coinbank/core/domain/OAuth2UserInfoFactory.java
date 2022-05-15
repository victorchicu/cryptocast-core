package com.coinbank.core.domain;

import com.coinbank.core.domain.facebook.FacebookOAuth2UserInfo;
import com.coinbank.core.domain.google.GoogleOAuth2UserInfo;
import com.coinbank.core.domain.github.GithubOAuth2UserInfo;
import com.coinbank.core.enums.OAuth2Provider;
import com.coinbank.core.domain.exceptions.OAuth2AuthenticationProcessingException;

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
