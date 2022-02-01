package com.trader.core.controllers;

import com.trader.core.binance.domain.account.AssetBalance;
import com.trader.core.dto.AssetBalanceDto;
import com.trader.core.dto.ChipDto;
import com.trader.core.exceptions.UserNotFoundException;
import com.trader.core.services.AssetService;
import com.trader.core.services.ExchangeService;
import com.trader.core.services.ExchangeStrategy;
import com.trader.core.services.UserService;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/assets")
public class AssetController {
    private final UserService userService;
    private final AssetService assetService;
    private final ExchangeStrategy exchangeStrategy;
    private final ConversionService conversionService;

    public AssetController(
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

    @GetMapping
    public List<AssetBalanceDto> listAssets(Principal principal) {
        return userService.findById(principal.getName())
                .map(user ->
                        assetService.listAssetBalances(user).stream()
                                .map(this::toAssetBalanceDto)
                                .collect(Collectors.toList())
                )
                .orElseThrow(UserNotFoundException::new);
    }

    @GetMapping("/available")
    public List<ChipDto> availableAssets(Principal principal) {
        return userService.findById(principal.getName())
                .map(user -> {
                    ExchangeService exchangeService = exchangeStrategy.getExchangeService(
                            user.getExchangeProvider()
                    );
                    return exchangeService.listSymbols();
                })
                .map((Set<String> symbols) -> symbols.stream()
                        .map(ChipDto::new)
                        .collect(Collectors.toList())
                )
                .orElseThrow(UserNotFoundException::new);
    }

    private AssetBalanceDto toAssetBalanceDto(AssetBalance assetBalance) {
        return conversionService.convert(assetBalance, AssetBalanceDto.class);
    }
}
