package com.trader.core.controllers;

import com.trader.core.domain.Chip;
import com.trader.core.dto.ChipDto;
import com.trader.core.exceptions.UserNotFoundException;
import com.trader.core.services.ChipsService;
import com.trader.core.services.UserService;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/chips")
public class ChipsController {
    private final UserService userService;
    private final ChipsService chipsService;
    private final ConversionService conversionService;

    public ChipsController(
            UserService userService,
            ChipsService chipsService,
            ConversionService conversionService
    ) {
        this.userService = userService;
        this.chipsService = chipsService;
        this.conversionService = conversionService;
    }

    @DeleteMapping("/{name}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeChip(Principal principal, @PathVariable String name) {
        userService.findById(principal.getName())
                .ifPresent(user ->
                        chipsService.removeChip(name, user)
                );
    }

    @PostMapping
    public ChipDto addChip(Principal principal, @RequestBody ChipDto chipDto) {
        return userService.findById(principal.getName())
                .map(user -> {
                    Chip chip = toChip(chipDto);
                    return chipsService.addChip(chip);
                })
                .map(this::toChipDto)
                .orElseThrow(UserNotFoundException::new);
    }

    @GetMapping
    public List<ChipDto> listChips(Principal principal) {
        return userService.findById(principal.getName())
                .map(chipsService::listChips)
                .map(chips -> chips.stream()
                        .map(this::toChipDto)
                        .collect(Collectors.toList())
                )
                .orElseThrow(UserNotFoundException::new);
    }


    private Chip toChip(ChipDto chipDto) {
        return conversionService.convert(chipDto, Chip.class);
    }

    private ChipDto toChipDto(Chip chip) {
        return conversionService.convert(chip, ChipDto.class);
    }
}
