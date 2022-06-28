package com.coinbank.core.web.controllers;

import com.coinbank.core.domain.AssetBalance;
import com.coinbank.core.domain.exceptions.UserNotFoundException;
import com.coinbank.core.services.WalletBalanceService;
import com.coinbank.core.services.UserService;
import com.coinbank.core.web.dto.AssetBalanceDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/wallets/{label}/overview")
public class WalletsOverviewController {
    private static final Logger LOG = LoggerFactory.getLogger(WalletsOverviewController.class);

    private final UserService userService;
    private final ConversionService conversionService;
    private final WalletBalanceService walletBalanceService;

    public WalletsOverviewController(
            UserService userService,
            ConversionService conversionService,
            WalletBalanceService walletBalanceService
    ) {
        this.userService = userService;
        this.conversionService = conversionService;
        this.walletBalanceService = walletBalanceService;
    }

    @GetMapping
    public List<AssetBalanceDto> listAssets(Principal principal, @PathVariable String label) {
        return userService.findById(principal.getName())
                .map(user ->
                        walletBalanceService.list(user, label).stream()
                                .map(this::toAssetBalanceDto)
                                .collect(Collectors.toList())
                )
                .orElseThrow(UserNotFoundException::new);
    }

    private AssetBalanceDto toAssetBalanceDto(AssetBalance assetBalance) {
        return conversionService.convert(assetBalance, AssetBalanceDto.class);
    }
}
