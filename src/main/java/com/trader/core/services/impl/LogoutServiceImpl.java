package com.trader.core.services.impl;

import com.trader.core.domain.Subscription;
import com.trader.core.services.AssetService;
import com.trader.core.services.LogoutService;
import com.trader.core.services.SubscriptionService;
import com.trader.core.services.UserService;
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
    private final SubscriptionService subscriptionService;

    public LogoutServiceImpl(
            UserService userService,
            AssetService assetService,
            SubscriptionService subscriptionService
    ) {
        this.userService = userService;
        this.assetService = assetService;
        this.subscriptionService = subscriptionService;
    }

    public void logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            userService.findById(authentication.getName())
                    .ifPresent(user -> {
                        Page<Subscription> subscriptions = subscriptionService.findSubscriptions(user, Pageable.unpaged());
                        subscriptions.forEach(subscription ->
                                assetService.removeAssetTickerEvent(user, subscription.getAssetName())
                        );
                    });
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
    }
}
