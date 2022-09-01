package ai.cryptocast.core.web.controllers;

import ai.cryptocast.core.domain.exceptions.NotFoundEmailException;
import ai.cryptocast.core.services.TokenProviderService;
import ai.cryptocast.core.services.UserService;
import ai.cryptocast.core.web.dto.AccessTokenDto;
import ai.cryptocast.core.web.dto.SignInDto;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/signin")
public class SignInController {
    private final UserService userService;
    private final TokenProviderService tokenProviderService;
    private final AuthenticationManager authenticationManager;

    public SignInController(
            UserService userService,
            TokenProviderService tokenProviderService,
            AuthenticationManager authenticationManager
    ) {
        this.userService = userService;
        this.tokenProviderService = tokenProviderService;
        this.authenticationManager = authenticationManager;
    }
    @PostMapping
    public AccessTokenDto signIn(@RequestBody SignInDto signinDto) {
        return userService.findByEmail(signinDto.getEmail())
                .map(user -> {
                    Authentication authentication = authenticationManager.authenticate(
                            createAuthToken(
                                    user.getId(),
                                    signinDto.getPassword()
                            )
                    );
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    return new AccessTokenDto(tokenProviderService.createToken(authentication));
                })
                .orElseThrow(() -> new NotFoundEmailException(signinDto.getEmail()));
    }

    private UsernamePasswordAuthenticationToken createAuthToken(String principal, String password) {
        return new UsernamePasswordAuthenticationToken(principal, password);
    }
}
