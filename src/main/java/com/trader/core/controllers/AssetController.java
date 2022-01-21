package com.trader.core.controllers;

import com.trader.core.dto.AssetBalanceDto;
import com.trader.core.services.AssetService;
import com.trader.core.binance.domain.account.AssetBalance;
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
    private final AssetService assetService;
    private final ConversionService conversionService;

    public AssetController(AssetService assetService, ConversionService conversionService) {
        this.assetService = assetService;
        this.conversionService = conversionService;
    }

    @GetMapping
    public List<AssetBalanceDto> listAssets(Principal principal) {
        return assetService.listAssetBalances(principal).stream()
                .map(this::toAssetBalanceDto)
                .collect(Collectors.toList());
    }


    private AssetBalanceDto toAssetBalanceDto(AssetBalance assetBalance) {
        return conversionService.convert(assetBalance, AssetBalanceDto.class);
    }
}
