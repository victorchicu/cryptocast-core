package com.trader.core.controllers;

import com.trader.core.domain.Candlestick;
import com.trader.core.dto.CandlestickDto;
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
@RequestMapping("/api/candlestick/{assetName}")
public class CandlestickController {
    private static final Logger LOG = LoggerFactory.getLogger(CandlestickController.class);

    private final UserService userService;
    private final ExchangeStrategy exchangeStrategy;
    private final ConversionService conversionService;

    public CandlestickController(
            UserService userService,
            ExchangeStrategy exchangeStrategy,
            ConversionService conversionService
    ) {
        this.userService = userService;
        this.exchangeStrategy = exchangeStrategy;
        this.conversionService = conversionService;
    }

    @GetMapping
    public List<CandlestickDto> getCandlestick(
            Principal principal,
            @PathVariable String assetName,
            @RequestParam("interval") String interval,
            @RequestParam(value = "startTime", required = false) Long startTime,
            @RequestParam(value = "endTime", required = false) Long endTime
    ) {
        return userService.findById(principal.getName())
                .map(user -> {
                    ExchangeService exchangeService = exchangeStrategy.getExchangeService(user.getExchangeProvider());
                    return exchangeService.getCandlestick(assetName, interval, startTime, endTime)
                            .stream()
                            .map(this::toCandlestickDto)
                            .collect(Collectors.toList());
                })
                .orElseThrow(UserNotFoundException::new);
    }


    private CandlestickDto toCandlestickDto(Candlestick candlestick) {
        return conversionService.convert(candlestick, CandlestickDto.class);
    }
}
