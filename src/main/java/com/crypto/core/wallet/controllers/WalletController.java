package com.crypto.core.wallet.controllers;

import com.crypto.core.binance.client.domain.wallet.Asset;
import com.crypto.core.wallet.dto.AssetDto;
import com.crypto.core.wallet.services.WalletService;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/wallet")
public class WalletController {
    private final WalletService walletService;
    private final ConversionService conversionService;

    public WalletController(WalletService walletService, ConversionService conversionService) {
        this.walletService = walletService;
        this.conversionService = conversionService;
    }

    @GetMapping("/assets")
    public List<AssetDto> listAssets(Principal principal) {
        return walletService.listAssets(principal).stream()
                .map(this::toAssetDto)
                .collect(Collectors.toList());
    }

    private AssetDto toAssetDto(Asset asset) {
        return conversionService.convert(asset, AssetDto.class);
    }
}
