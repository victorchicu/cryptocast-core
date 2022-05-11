package com.coinbank.core.controllers;

import com.coinbank.core.domain.Ohlc;
import com.coinbank.core.dto.OhlcDto;
import com.coinbank.core.enums.ExchangeProvider;
import com.coinbank.core.services.ExchangeService;
import com.coinbank.core.services.ExchangeStrategy;
import com.coinbank.core.services.UserService;
import com.coinbank.core.exceptions.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/ohlc/{assetName}")
public class OhlcController {
    private static final Logger LOG = LoggerFactory.getLogger(OhlcController.class);

    private final UserService userService;
    private final ExchangeStrategy exchangeStrategy;
    private final ConversionService conversionService;

    public OhlcController(
            UserService userService,
            ExchangeStrategy exchangeStrategy,
            ConversionService conversionService
    ) {
        this.userService = userService;
        this.exchangeStrategy = exchangeStrategy;
        this.conversionService = conversionService;
    }

    @GetMapping
    public List<OhlcDto> list(Principal principal, @PathVariable String assetName, @RequestParam String interval, @RequestParam(value = "start", required = false) Long start, @RequestParam(value = "end", required = false) Long end) {
        return userService.findById(principal.getName())
                .map(user -> {
                    ExchangeService exchangeService = exchangeStrategy.getExchangeService(ExchangeProvider.BINANCE);
                    return exchangeService.listOhlc(assetName, interval, start, end)
                            .stream()
                            .map(this::toOhlcDto)
                            .collect(Collectors.toList());
                })
                .orElseThrow(UserNotFoundException::new);
    }


    private OhlcDto toOhlcDto(Ohlc ohlc) {
        return conversionService.convert(ohlc, OhlcDto.class);
    }
}
