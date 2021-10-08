package com.crypto.core.auth.controllers;

import com.crypto.core.auth.dto.AccessTokenResponseDto;
import com.crypto.core.auth.dto.LoginRequestDto;
import com.crypto.core.users.exceptions.EmailAddressNotFoundException;
import com.crypto.core.users.services.UserService;
import com.crypto.core.auth.services.TokenProviderService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/login")
public class LoginController {
    private final UserService userService;
    private final TokenProviderService tokenProviderService;
    private final AuthenticationManager authenticationManager;

    public LoginController(
            UserService userService,
            TokenProviderService tokenProviderService,
            AuthenticationManager authenticationManager
    ) {
        this.userService = userService;
        this.tokenProviderService = tokenProviderService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping
    public AccessTokenResponseDto login(@RequestBody LoginRequestDto loginRequestDto) {
        return userService.findByEmail(loginRequestDto.getEmail())
                .map(user -> {
                    Authentication authentication = authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    loginRequestDto.getEmail(),
                                    loginRequestDto.getPassword()
                            )
                    );
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    String accessToken = tokenProviderService.createToken(authentication);
                    return new AccessTokenResponseDto(accessToken);
                })
                .orElseThrow(() -> new EmailAddressNotFoundException("Email address provided is not registered"));
    }
}
