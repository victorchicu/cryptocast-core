package com.coinbank.core.domain.services.impl;

import com.coinbank.core.domain.OAuth2UserInfo;
import com.coinbank.core.domain.OAuth2UserInfoFactory;
import com.coinbank.core.domain.User;
import com.coinbank.core.domain.UserPrincipal;
import com.coinbank.core.domain.exceptions.OAuth2AuthenticationProcessingException;
import com.coinbank.core.domain.services.UserService;
import com.coinbank.core.enums.OAuth2Provider;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final UserService userService;

    public CustomOAuth2UserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);
        try {
            return processOAuth2User(oAuth2UserRequest, oAuth2User);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            // Throwing an instance of AuthenticationException will trigger the OAuth2AuthenticationFailureHandler
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(oAuth2UserRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());
        if (StringUtils.isEmpty(oAuth2UserInfo.getEmail())) {
            throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
        }
        return UserPrincipal.create(
                userService.findById(oAuth2UserInfo.getEmail())
                        .map((User user) -> {
                            if (!user.getAuth2Provider().equals(OAuth2Provider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()))) {
                                throw new OAuth2AuthenticationProcessingException("Looks like you're signed up with " + user.getAuth2Provider() + " account. Please use your " + user.getAuth2Provider() + " account to login.");
                            }
                            user.setImageUrl(oAuth2UserInfo.getImageUrl());
                            user.setDisplayName(oAuth2UserInfo.getName());
                            return userService.save(user);
                        })
                        .orElseGet(() -> registerUser(oAuth2UserRequest, oAuth2UserInfo)),
                oAuth2User.getAttributes()
        );
    }

    private User registerUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) {
        User account = User.newBuilder()
                .id(oAuth2UserInfo.getName())
                .email(oAuth2UserInfo.getEmail())
                .imageUrl(oAuth2UserInfo.getImageUrl())
                .displayName(oAuth2UserInfo.getName())
                .auth2Provider(OAuth2Provider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()))
                .providerId(oAuth2UserInfo.getId())
                .build();
        return userService.save(account);
    }
}
