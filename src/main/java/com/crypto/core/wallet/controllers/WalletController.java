package com.crypto.core.wallet.controllers;

import com.crypto.core.binance.client.domain.wallet.Asset;
import com.crypto.core.binance.services.BinanceService;
import com.crypto.core.wallet.dto.AssetDto;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/wallet")
public class WalletController {
    private final BinanceService binanceService;
    private final ConversionService conversionService;

    public WalletController(BinanceService binanceService, ConversionService conversionService) {
        this.binanceService = binanceService;
        this.conversionService = conversionService;
    }

    @GetMapping("/assets")
    public List<AssetDto> listAssets() {
        return binanceService.listAssets().stream()
                .filter(asset -> new BigDecimal(asset.getFree()).compareTo(BigDecimal.ZERO) > 0)
                .map(this::toAssetDto)
                .sorted(Comparator.comparing(AssetDto::getBalance).reversed())
                .collect(Collectors.toList());
    }

    private AssetDto toAssetDto(Asset asset) {
        return conversionService.convert(asset, AssetDto.class);
    }
}
