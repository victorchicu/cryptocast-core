package com.coinbank.core.domain.services.impl;

import com.coinbank.core.domain.AssetTracker;
import com.coinbank.core.domain.services.UserService;
import com.coinbank.core.domain.services.AssetService;
import com.coinbank.core.domain.services.LogoutService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class LogoutServiceImpl implements LogoutService {
    private final UserService userService;
    private final AssetService assetService;

    public LogoutServiceImpl(UserService userService, AssetService assetService) {
        this.userService = userService;
        this.assetService = assetService;
    }

    public void logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
    }
}
