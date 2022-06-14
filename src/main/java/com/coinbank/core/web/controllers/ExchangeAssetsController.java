package com.coinbank.core.web.controllers;

import com.coinbank.core.domain.Asset;
import com.coinbank.core.domain.exceptions.UserNotFoundException;
import com.coinbank.core.domain.services.AssetService;
import com.coinbank.core.domain.services.UserService;
import com.coinbank.core.web.dto.AssetDto;
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
@RequestMapping("/api/exchanges/{exchange}/assets")
public class ExchangeAssetsController {
    private static final Logger LOG = LoggerFactory.getLogger(ExchangeAssetsController.class);

    private final UserService userService;
    private final AssetService assetService;
    private final ConversionService conversionService;

    public ExchangeAssetsController(UserService userService, AssetService assetService, ConversionService conversionService) {
        this.userService = userService;
        this.assetService = assetService;
        this.conversionService = conversionService;
    }

    @GetMapping
    public List<AssetDto> listAssets(Principal principal, @PathVariable String exchange) {
        return userService.findById(principal.getName())
                .map(user ->
                        assetService.listAssets(user, exchange).stream()
                                .map(this::toAssetDto)
                                .collect(Collectors.toList())
                )
                .orElseThrow(UserNotFoundException::new);
    }

    private AssetDto toAssetDto(Asset asset) {
        return conversionService.convert(asset, AssetDto.class);
    }
}
