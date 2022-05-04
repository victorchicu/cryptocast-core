package com.coinbank.core.controllers;

import com.coinbank.core.dto.AccessTokenDto;
import com.coinbank.core.dto.LoginRequestDto;
import com.coinbank.core.services.TokenProviderService;
import com.coinbank.core.services.UserService;
import com.coinbank.core.exceptions.EmailNotFoundException;
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
    public AccessTokenDto login(@RequestBody LoginRequestDto loginRequestDto) {
        return userService.findByEmailAndExchangeProvider(loginRequestDto.getEmail(), loginRequestDto.getExchangeProvider())
                .map(user -> {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                            user.getId(),
                            loginRequestDto.getPassword()
                    );
                    Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    String accessToken = tokenProviderService.createToken(authentication);
                    return new AccessTokenDto(accessToken);
                })
                .orElseThrow(EmailNotFoundException::new);
    }
}
