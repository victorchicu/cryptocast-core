package com.coinbank.core.services.impl;

import com.coinbank.core.services.UserService;
import com.coinbank.core.services.WalletBalanceService;
import com.coinbank.core.services.LogoutService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class LogoutServiceImpl implements LogoutService {
    private final UserService userService;
    private final WalletBalanceService walletBalanceService;

    public LogoutServiceImpl(UserService userService, WalletBalanceService walletBalanceService) {
        this.userService = userService;
        this.walletBalanceService = walletBalanceService;
    }

    public void logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
    }
}
