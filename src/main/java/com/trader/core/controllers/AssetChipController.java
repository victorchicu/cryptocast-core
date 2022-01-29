package com.trader.core.controllers;

import com.trader.core.domain.AssetChip;
import com.trader.core.dto.AssetChipDto;
import com.trader.core.exceptions.UserNotFoundException;
import com.trader.core.services.AssetChipService;
import com.trader.core.services.UserService;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/chips")
public class AssetChipController {
    private final UserService userService;
    private final AssetChipService assetChipService;
    private final ConversionService conversionService;

    public AssetChipController(
            UserService userService,
            AssetChipService assetChipService,
            ConversionService conversionService
    ) {
        this.userService = userService;
        this.assetChipService = assetChipService;
        this.conversionService = conversionService;
    }

    @DeleteMapping("/{assetName}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAssetChip(Principal principal, @PathVariable String assetName) {
        userService.findById(principal.getName())
                .ifPresent(user ->
                        assetChipService.removeAssetChip(assetName, user)
                );
    }

    @PostMapping
    public AssetChipDto addAssetChip(Principal principal, @RequestBody AssetChipDto assetChipDto) {
        return userService.findById(principal.getName())
                .map(user -> {
                    AssetChip assetChip = toAssetChip(assetChipDto);
                    return assetChipService.addAssetChip(assetChip);
                })
                .map(this::toAssetChipDto)
                .orElseThrow(UserNotFoundException::new);
    }

    @GetMapping
    public List<AssetChipDto> listAssetChips(Principal principal) {
        return userService.findById(principal.getName())
                .map(assetChipService::listAssetChips)
                .map(assetChips ->
                        assetChips.stream()
                                .map(this::toAssetChipDto)
                                .collect(Collectors.toList())
                )
                .orElseThrow(UserNotFoundException::new);
    }


    private AssetChip toAssetChip(AssetChipDto assetChipDto) {
        return conversionService.convert(assetChipDto, AssetChip.class);
    }

    private AssetChipDto toAssetChipDto(AssetChip assetChip) {
        return conversionService.convert(assetChip, AssetChipDto.class);
    }
}
