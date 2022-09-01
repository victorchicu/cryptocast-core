package ai.cryptocast.core.services.impl;

import ai.cryptocast.core.domain.User;
import ai.cryptocast.core.domain.UserPrincipal;
import ai.cryptocast.core.domain.exceptions.OAuth2AuthenticationProcessingException;
import ai.cryptocast.core.domain.oauth2.OAuth2UserInfo;
import ai.cryptocast.core.domain.oauth2.OAuth2UserInfoFactory;
import ai.cryptocast.core.services.UserService;
import ai.cryptocast.core.enums.OAuth2Provider;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private static final Logger LOG = LoggerFactory.getLogger(CustomOAuth2UserService.class);

    private final UserService userService;

    public CustomOAuth2UserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);
        try {
            return findOrRegisterOAuth2User(oAuth2UserRequest, oAuth2User);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            // Throwing an instance of AuthenticationException will trigger the OAuth2AuthenticationFailureHandler
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private User registerOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) {
        return userService.save(
                User.newBuilder()
                        .id(oAuth2UserInfo.getId())
                        .email(oAuth2UserInfo.getEmail())
                        .imageUrl(oAuth2UserInfo.getImageUrl())
                        .displayName(oAuth2UserInfo.getName())
                        .auth2Provider(
                                OAuth2Provider.fromString(
                                        oAuth2UserRequest.getClientRegistration().getRegistrationId()
                                )
                        )
                        .providerId(oAuth2UserInfo.getId())
                        .build()
        );
    }

    private OAuth2User findOrRegisterOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(
                oAuth2UserRequest.getClientRegistration().getRegistrationId(),
                oAuth2User.getAttributes()
        );
        if (StringUtils.isEmpty(oAuth2UserInfo.getEmail())) {
            throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
        }
        return UserPrincipal.create(
                userService.findByEmail(oAuth2UserInfo.getEmail())
                        .map(user -> {
                            if (!user.getAuth2Provider().equals(OAuth2Provider.fromString(oAuth2UserRequest.getClientRegistration().getRegistrationId()))) {
                                throw new OAuth2AuthenticationProcessingException("Looks like you're signed up with " + user.getAuth2Provider() + " account. Please use your " + user.getAuth2Provider() + " account to login.");
                            } else {
                                user.setImageUrl(oAuth2UserInfo.getImageUrl());
                                user.setDisplayName(oAuth2UserInfo.getName());
                                return userService.save(user);
                            }
                        })
                        .orElseGet(() -> registerOAuth2User(oAuth2UserRequest, oAuth2UserInfo)),
                oAuth2User.getAttributes()
        );
    }
}
