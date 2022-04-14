package com.trader.core.controllers;

import com.trader.core.domain.Asset;
import com.trader.core.domain.AssetPrice;
import com.trader.core.dto.AssetDto;
import com.trader.core.dto.AssetPriceDto;
import com.trader.core.dto.ChipDto;
import com.trader.core.exceptions.AssetNotFoundException;
import com.trader.core.exceptions.UserNotFoundException;
import com.trader.core.services.AssetService;
import com.trader.core.services.ExchangeService;
import com.trader.core.services.ExchangeStrategy;
import com.trader.core.services.UserService;
import org.apache.commons.collections4.SetUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{assetName}")
    public AssetDto getAsset(Principal principal, @PathVariable String assetName) {
        return userService.findById(principal.getName())
                .map(user -> {
                    ExchangeService exchangeService = exchangeStrategy.getExchangeService(user.getExchangeProvider());
                    return exchangeService.findAssetByName(user, assetName)
                            .map(this::toAssetDto)
                            .orElseThrow(() -> new AssetNotFoundException(assetName));
                })
                .orElseThrow(UserNotFoundException::new);
    }

    @GetMapping("/{assetName}/price")
    public AssetPriceDto getAssetPrice(Principal principal, @PathVariable String assetName) {
        return userService.findById(principal.getName())
                .map(user -> {
                    ExchangeService exchangeService = exchangeStrategy.getExchangeService(user.getExchangeProvider());
                    return exchangeService.getAssetPrice(user, assetName)
                            .map(this::toAssetPriceDto)
                            .orElseThrow(() -> new AssetNotFoundException(assetName));
                })
                .orElseThrow(UserNotFoundException::new);
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
    public List<AssetDto> listAssets(Principal principal, @RequestParam(value = "assets", required = false) Set<String> assets) {
        return userService.findById(principal.getName())
                .map(user ->
                        assetService.listAssets(user, SetUtils.emptyIfNull(assets)).stream()
                                .filter(suppressUnsupportedAssets())
                                .map(this::toAssetDto)
                                .collect(Collectors.toList())
                )
                .orElseThrow(UserNotFoundException::new);
    }


    private AssetDto toAssetDto(Asset asset) {
        return conversionService.convert(asset, AssetDto.class);
    }

    private AssetPriceDto toAssetPriceDto(AssetPrice assetPrice) {
        return conversionService.convert(assetPrice, AssetPriceDto.class);
    }

    private Predicate<Asset> suppressUnsupportedAssets() {
        return asset -> {
            try {
                return this.toAssetDto(asset) != null;
            } catch (Exception e) {
                LOG.warn("Asset {0} is not supported ", asset.getAsset());
                return false;
            }
        };
    }
}
