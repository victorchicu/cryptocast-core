package ai.cryptocast.core.web.controllers;

import ai.cryptocast.core.domain.AssetBalance;
import ai.cryptocast.core.domain.exceptions.UserNotFoundException;
import ai.cryptocast.core.services.WalletBalanceService;
import ai.cryptocast.core.services.UserService;
import ai.cryptocast.core.web.dto.AssetBalanceDto;
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
@RequestMapping("/api/wallets/{label}/balance")
public class WalletBalanceController {
    private static final Logger LOG = LoggerFactory.getLogger(WalletBalanceController.class);

    private final UserService userService;
    private final ConversionService conversionService;
    private final WalletBalanceService walletBalanceService;

    public WalletBalanceController(
            UserService userService,
            ConversionService conversionService,
            WalletBalanceService walletBalanceService
    ) {
        this.userService = userService;
        this.conversionService = conversionService;
        this.walletBalanceService = walletBalanceService;
    }

    @GetMapping
    public List<AssetBalanceDto> listAssetBalances(Principal principal, @PathVariable String label) {
        return userService.findById(principal.getName())
                .map(user -> {
                    List<AssetBalanceDto> list = walletBalanceService.list(user, label).stream()
                            .map(this::toAssetBalanceDto)
                            .collect(Collectors.toList());
                    return list;
                })
                .orElseThrow(UserNotFoundException::new);
    }

    private AssetBalanceDto toAssetBalanceDto(AssetBalance assetBalance) {
        return conversionService.convert(assetBalance, AssetBalanceDto.class);
    }
}
