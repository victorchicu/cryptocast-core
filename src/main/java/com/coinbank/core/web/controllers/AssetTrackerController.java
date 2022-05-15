package com.coinbank.core.web.controllers;

import com.coinbank.core.domain.AssetTracker;
import com.coinbank.core.web.dto.AssetTrackerDto;
import com.coinbank.core.domain.exceptions.AssetTrackerNotFoundException;
import com.coinbank.core.domain.exceptions.UserNotFoundException;
import com.coinbank.core.domain.services.AssetService;
import com.coinbank.core.domain.services.AssetTrackerService;
import com.coinbank.core.domain.services.UserService;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/trackers")
public class AssetTrackerController {
    private final UserService userService;
    private final AssetService assetService;
    private final ConversionService conversionService;
    private final AssetTrackerService assetTrackerService;

    public AssetTrackerController(
            UserService userService,
            AssetService assetService,
            ConversionService conversionService,
            AssetTrackerService assetTrackerService
    ) {
        this.userService = userService;
        this.assetService = assetService;
        this.conversionService = conversionService;
        this.assetTrackerService = assetTrackerService;
    }


    @DeleteMapping("/{assetName}/remove")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeAssetTracker(Principal principal, @PathVariable String assetName) {
        userService.findById(principal.getName())
                .ifPresentOrElse(user -> assetTrackerService.findByAssetName(user, assetName)
                        .ifPresentOrElse(subscription -> {
                            assetService.removeAssetTickerEvent(user, subscription.getAssetName());
                            assetTrackerService.deleteById(subscription.getId());
                        }, AssetTrackerNotFoundException::new), () -> new UserNotFoundException()
                );
    }

    @PostMapping("/{assetName}/add")
    public AssetTrackerDto addAssetTracker(Principal principal, @PathVariable String assetName) {
        return userService.findById(principal.getName())
                .map(user -> {
                    AssetTrackerDto subscription = assetTrackerService.findByAssetName(user, assetName)
                            .map(this::toAssetTrackerDto)
                            .orElseGet(() -> toAssetTrackerDto(
                                    assetTrackerService.save(
                                            AssetTracker.newBuilder()
                                                    .assetName(assetName)
                                                    .build()
                                    ))
                            );
                    assetService.addAssetTickerEvent(user, subscription.getAssetName());
                    return subscription;
                })
                .orElseThrow(() -> new UserNotFoundException());
    }

    @GetMapping("/{assetName}")
    public AssetTrackerDto getAssetTracker(Principal principal, @PathVariable String assetName) {
        return userService.findById(principal.getName())
                .map(user ->
                        assetTrackerService.findByAssetName(user, assetName)
                                .map(subscription -> {
                                    assetService.removeAssetTickerEvent(user, subscription.getAssetName());
                                    assetService.addAssetTickerEvent(user, subscription.getAssetName());
                                    return subscription;
                                })
                                .map(this::toAssetTrackerDto)
                                .orElseThrow(AssetTrackerNotFoundException::new)
                )
                .orElseThrow(() -> new UserNotFoundException());
    }

    @GetMapping
    public Page<AssetTrackerDto> listAssetTrackers(Principal principal, Pageable pageable) {
        return userService.findById(principal.getName())
                .map(user -> assetTrackerService.findAll(user, pageable)
                        .map(this::toAssetTrackerDto)
                )
                .orElseThrow(() -> new UserNotFoundException());
    }

    private AssetTrackerDto toAssetTrackerDto(AssetTracker assetTracker) {
        return conversionService.convert(assetTracker, AssetTrackerDto.class);
    }
}
