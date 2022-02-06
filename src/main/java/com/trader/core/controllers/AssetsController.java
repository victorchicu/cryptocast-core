package com.trader.core.controllers;

import com.trader.core.domain.AssetBalance;
import com.trader.core.dto.ChipDto;
import com.trader.core.dto.AssetBalanceDto;
import com.trader.core.exceptions.UserNotFoundException;
import com.trader.core.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/assets")
public class AssetsController {
    private static final Logger LOG = LoggerFactory.getLogger(AssetsController.class);

    private final UserService userService;
    private final AssetService assetService;
    private final ExchangeStrategy exchangeStrategy;
    private final ConversionService conversionService;

    public AssetsController(
            UserService userService,
            AssetService assetService,
            ExchangeStrategy exchangeStrategy,
            ConversionService conversionService
    ) {
        this.userService = userService;
        this.assetService = assetService;
        this.exchangeStrategy = exchangeStrategy;
        this.conversionService = conversionService;
    }

    @GetMapping("/available")
    public List<ChipDto> availableAssets(Principal principal) {
        return userService.findById(principal.getName())
                .map(user -> {
                    ExchangeService exchangeService = exchangeStrategy.getExchangeService(user.getExchangeProvider());
                    return exchangeService.availableAssets();
                })
                .map((Set<String> symbols) -> symbols.stream()
                        .map(ChipDto::new)
                        .collect(Collectors.toList())
                )
                .orElseThrow(UserNotFoundException::new);
    }

    @GetMapping
    public List<AssetBalanceDto> listAssetsBalances(Principal principal) {
        return userService.findById(principal.getName())
                .map(user ->
                        assetService.listAssetsBalances(user).stream()
                                .filter(suppressNotSupportedAssets())
                                .map(this::toAssetBalanceDto)
                                .collect(Collectors.toList())
                )
                .orElseThrow(UserNotFoundException::new);
    }


    private AssetBalanceDto toAssetBalanceDto(AssetBalance assetBalance) {
        return conversionService.convert(assetBalance, AssetBalanceDto.class);
    }

    private Predicate<AssetBalance> suppressNotSupportedAssets() {
        return assetBalance -> {
            try {
                return this.toAssetBalanceDto(assetBalance) != null;
            } catch (Exception e) {
                LOG.warn("Asset {0} is not supported " + assetBalance.getAsset());
                return false;
            }
        };
    }
}
