package com.trader.core.assets.controllers;

import com.trader.core.assets.dto.AssetBalanceDto;
import com.trader.core.assets.services.AssetService;
import com.trader.core.binance.client.domain.account.AssetBalance;
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
