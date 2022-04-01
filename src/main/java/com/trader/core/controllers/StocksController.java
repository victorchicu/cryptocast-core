package com.trader.core.controllers;

import com.trader.core.domain.Ohlc;
import com.trader.core.dto.OhlcDto;
import com.trader.core.exceptions.UserNotFoundException;
import com.trader.core.services.ExchangeService;
import com.trader.core.services.ExchangeStrategy;
import com.trader.core.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/stocks/{assetName}")
public class StocksController {
    private static final Logger LOG = LoggerFactory.getLogger(StocksController.class);

    private final UserService userService;
    private final ExchangeStrategy exchangeStrategy;
    private final ConversionService conversionService;

    public StocksController(
            UserService userService,
            ExchangeStrategy exchangeStrategy,
            ConversionService conversionService
    ) {
        this.userService = userService;
        this.exchangeStrategy = exchangeStrategy;
        this.conversionService = conversionService;
    }

    @GetMapping
    public List<OhlcDto> listOhlc(
            Principal principal,
            @PathVariable String assetName,
            @RequestParam("interval") String interval,
            @RequestParam(value = "startTime", required = false) Long startTime,
            @RequestParam(value = "endTime", required = false) Long endTime
    ) {
        return userService.findById(principal.getName())
                .map(user -> {
                    ExchangeService exchangeService = exchangeStrategy.getExchangeService(user.getExchangeProvider());
                    return exchangeService.listOhlc(assetName, interval, startTime, endTime)
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
