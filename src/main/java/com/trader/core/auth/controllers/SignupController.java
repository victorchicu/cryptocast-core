package com.trader.core.auth.controllers;

import com.trader.core.users.domain.User;
import com.trader.core.auth.dto.AccessTokenResponseDto;
import com.trader.core.auth.dto.SignupRequestDto;
import com.trader.core.users.exceptions.EmailException;
import com.trader.core.auth.services.TokenProviderService;
import com.trader.core.users.services.UserService;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/signup")
public class SignupController {
    private final UserService userService;
    private final ConversionService conversionService;
    private final TokenProviderService tokenProviderService;
    private final AuthenticationManager authenticationManager;

    public SignupController(
            UserService userService,
            ConversionService conversionService,
            TokenProviderService tokenProviderService,
            AuthenticationManager authenticationManager
    ) {
        this.userService = userService;
        this.conversionService = conversionService;
        this.tokenProviderService = tokenProviderService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping
    public AccessTokenResponseDto signup(@RequestBody SignupRequestDto signupRequestDto) {
        if (userService.findByEmail(signupRequestDto.getEmail()).isPresent()) {
            throw new EmailException("Email address already in use");
        }
        userService.save(toUser(signupRequestDto));
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signupRequestDto.getEmail(),
                        signupRequestDto.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String accessToken = tokenProviderService.createToken(authentication);
        return new AccessTokenResponseDto(accessToken);
    }

    private User toUser(SignupRequestDto signupRequestDto) {
        return conversionService.convert(signupRequestDto, User.class);
    }
}
