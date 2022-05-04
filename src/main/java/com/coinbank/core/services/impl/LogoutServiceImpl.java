package com.coinbank.core.services.impl;

import com.coinbank.core.domain.AssetTracker;
import com.coinbank.core.services.AssetService;
import com.coinbank.core.services.LogoutService;
import com.coinbank.core.services.AssetTrackerService;
import com.coinbank.core.services.UserService;
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
    private final AssetTrackerService assetTrackerService;

    public LogoutServiceImpl(
            UserService userService,
            AssetService assetService,
            AssetTrackerService assetTrackerService
    ) {
        this.userService = userService;
        this.assetService = assetService;
        this.assetTrackerService = assetTrackerService;
    }

    public void logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            userService.findById(authentication.getName())
                    .ifPresent(user -> {
                        Page<AssetTracker> subscriptions = assetTrackerService.findAll(user, Pageable.unpaged());
                        subscriptions.forEach(subscription ->
                                assetService.removeAssetTickerEvent(user, subscription.getAssetName())
                        );
                    });
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
    }
}
