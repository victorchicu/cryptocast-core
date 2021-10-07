package com.crypto.core.auth.controllers;

import com.crypto.core.auth.dto.AccessTokenResponseDto;
import com.crypto.core.auth.dto.AuthRequestDto;
import com.crypto.core.users.exceptions.NotFoundUserException;
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
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;
    private final TokenProviderService tokenProviderService;
    private final AuthenticationManager authenticationManager;

    public AuthController(
            UserService userService,
            TokenProviderService tokenProviderService,
            AuthenticationManager authenticationManager
    ) {
        this.userService = userService;
        this.tokenProviderService = tokenProviderService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping
    public AccessTokenResponseDto authorize(@RequestBody AuthRequestDto authRequestDto) {
        return userService.findByEmail(authRequestDto.getEmail())
                .map(user -> {
                    Authentication authentication = authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    authRequestDto.getEmail(),
                                    authRequestDto.getPassword()
                            )
                    );
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    String accessToken = tokenProviderService.createToken(authentication);
                    return new AccessTokenResponseDto(accessToken);
                })
                .orElseThrow(() -> new NotFoundUserException("Email address provided is not registered"));
    }
}
