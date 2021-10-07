package com.crypto.core.auth.controllers;

import com.crypto.core.auth.domain.Account;
import com.crypto.core.auth.dto.AccessTokenResponseDto;
import com.crypto.core.auth.dto.AuthRequestDto;
import com.crypto.core.auth.dto.SignupRequestDto;
import com.crypto.core.auth.exceptions.AccountException;
import com.crypto.core.auth.exceptions.NotFoundAccountException;
import com.crypto.core.auth.services.AccountService;
import com.crypto.core.oauth2.services.TokenProviderService;
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
    private final AccountService accountService;
    private final ConversionService conversionService;
    private final TokenProviderService tokenProviderService;
    private final AuthenticationManager authenticationManager;

    public SignupController(
            AccountService accountService,
            ConversionService conversionService,
            TokenProviderService tokenProviderService,
            AuthenticationManager authenticationManager
    ) {
        this.accountService = accountService;
        this.conversionService = conversionService;
        this.tokenProviderService = tokenProviderService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping
    public AccessTokenResponseDto signup(@RequestBody SignupRequestDto signupRequestDto) {
        if (accountService.findByEmail(signupRequestDto.getEmail()).isPresent()) {
            throw new AccountException("Email address already in use");
        } else {
            Account account = toAccount(signupRequestDto);
            account = accountService.save(account);
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            account.getEmail(),
                            account.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String accessToken = tokenProviderService.createToken(authentication);
            return new AccessTokenResponseDto(accessToken);
        }
    }

    private Account toAccount(SignupRequestDto signupRequestDto) {
        return conversionService.convert(signupRequestDto, Account.class);
    }
}
