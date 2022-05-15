package com.coinbank.core.web.controllers;

import com.coinbank.core.domain.Asset;
import com.coinbank.core.domain.AssetPrice;
import com.coinbank.core.web.dto.AssetDto;
import com.coinbank.core.web.dto.AssetPriceDto;
import com.coinbank.core.web.dto.ChipDto;
import com.coinbank.core.domain.exceptions.UserNotFoundException;
import com.coinbank.core.domain.services.AssetService;
import com.coinbank.core.domain.services.ExchangeProvider;
import com.coinbank.core.domain.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/assets")
public class AssetsController {
    private static final Logger LOG = LoggerFactory.getLogger(AssetsController.class);

    private final UserService userService;
    private final AssetService assetService;
    private final ExchangeProvider exchangeProvider;
    private final ConversionService conversionService;

    public AssetsController(
            UserService userService,
            AssetService assetService,
            ExchangeProvider exchangeProvider,
            ConversionService conversionService
    ) {
        this.userService = userService;
        this.assetService = assetService;
        this.exchangeProvider = exchangeProvider;
        this.conversionService = conversionService;
    }

    @GetMapping("/{assetName}")
    public AssetDto getAsset(Principal principal, @PathVariable String assetName) {
        throw new UnsupportedOperationException();
//        return userService.findById(principal.getName())
//                .map(user -> {
//                    ExchangeService exchangeService = exchangeFactory.create(, ExchangeProvider.BINANCE);
//                    return exchangeService.findAssetByName(user, assetName)
//                            .map(this::toAssetDto)
//                            .orElseThrow(() -> new AssetNotFoundException(assetName));
//                })
//                .orElseThrow(UserNotFoundException::new);
    }

    @GetMapping("/{assetName}/price")
    public AssetPriceDto getAssetPrice(Principal principal, @PathVariable String assetName) {
        throw new UnsupportedOperationException();
//        return userService.findById(principal.getName())
//                .map(user -> {
//                    ExchangeService exchangeService = exchangeFactory.create(, ExchangeProvider.BINANCE);
//                    return exchangeService.getAssetPrice(user, assetName)
//                            .map(this::toAssetPriceDto)
//                            .orElseThrow(() -> new AssetNotFoundException(assetName));
//                })
//                .orElseThrow(() -> new UserNotFoundException());
    }

    @GetMapping("/available")
    public List<ChipDto> availableAssets(Principal principal) {
        throw new UnsupportedOperationException();
//        return userService.findById(principal.getName())
//                .map(user -> {
//                    ExchangeService exchangeService = exchangeFactory.create(, ExchangeProvider.BINANCE);
//                    return exchangeService.availableAssets();
//                })
//                .map((Set<String> symbols) -> symbols.stream()
//                        .map(ChipDto::new)
//                        .collect(Collectors.toList())
//                )
//                .orElseThrow(() -> new UserNotFoundException());
    }

    @GetMapping
    public List<AssetDto> listAssets(Principal principal) {
        return userService.findById(principal.getName())
                .map(user -> assetService.listAssets(user).stream()
                        .map(this::toAssetDto)
                        .collect(Collectors.toList())
                )
                .orElseThrow(() -> new UserNotFoundException());
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
                LOG.warn("Asset {0} is not supported ", asset.getName());
                return false;
            }
        };
    }
}
