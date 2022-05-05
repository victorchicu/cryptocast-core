package com.coinbank.core.controllers;

import com.coinbank.core.domain.User;
import com.coinbank.core.dto.AccessTokenDto;
import com.coinbank.core.dto.SignupDto;
import com.coinbank.core.enums.ExchangeProvider;
import com.coinbank.core.services.TokenProviderService;
import com.coinbank.core.services.UserService;
import com.coinbank.core.exceptions.EmailNotFoundException;
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
    public AccessTokenDto signup(@RequestBody SignupDto signupDto) {
        String email = signupDto.getEmail();
        ExchangeProvider provider = signupDto.getExchangeProvider();
        if (userService.findByEmailAndExchangeProvider(email, provider).isPresent()) {
            throw new EmailNotFoundException();
        }
        User user = toUser(signupDto);
        user = userService.save(user);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getId(),
                        signupDto.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String accessToken = tokenProviderService.createToken(authentication);
        return new AccessTokenDto(accessToken);
    }

    private User toUser(SignupDto signupDto) {
        return conversionService.convert(signupDto, User.class);
    }
}
