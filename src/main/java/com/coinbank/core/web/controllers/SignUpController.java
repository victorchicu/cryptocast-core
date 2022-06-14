package com.coinbank.core.web.controllers;

import com.coinbank.core.domain.User;
import com.coinbank.core.web.dto.AccessTokenDto;
import com.coinbank.core.web.dto.SignUpDto;
import com.coinbank.core.domain.exceptions.EmailException;
import com.coinbank.core.domain.services.TokenProviderService;
import com.coinbank.core.domain.services.UserService;
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
public class SignUpController {
    private final UserService userService;
    private final ConversionService conversionService;
    private final TokenProviderService tokenProviderService;
    private final AuthenticationManager authenticationManager;

    public SignUpController(
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
    public AccessTokenDto signUp(@RequestBody SignUpDto signupDto) {
        String email = signupDto.getEmail();
        if (userService.findByEmail(email).isPresent()) {
            throw new EmailException(String.format("%s is taken", email));
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

    private User toUser(SignUpDto signupDto) {
        return conversionService.convert(signupDto, User.class);
    }
}
