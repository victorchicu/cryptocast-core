package com.crypto.core.binance.assets.controllers;

import com.crypto.core.binance.client.domain.account.AssetBalance;
import com.crypto.core.binance.assets.dto.AssetBalanceDto;
import com.crypto.core.binance.assets.services.AssetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/assets")
public class AssetController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AssetController.class);

    private final AssetService assetService;
    private final ConversionService conversionService;

    public AssetController(AssetService assetService, ConversionService conversionService) {
        this.assetService = assetService;
        this.conversionService = conversionService;
    }

    @GetMapping
    public List<AssetBalanceDto> listAssets(Principal principal) {
        return assetService.listAssets(principal).stream()
                .map(this::toAssetBalanceDto)
                .collect(Collectors.toList());
    }


    private AssetBalanceDto toAssetBalanceDto(AssetBalance assetBalance) {
        return conversionService.convert(assetBalance, AssetBalanceDto.class);
    }
}
