package com.coinbank.core.controllers;

import com.coinbank.core.domain.Ohlc;
import com.coinbank.core.dto.OhlcDto;
import com.coinbank.core.services.ExchangeProvider;
import com.coinbank.core.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/ohlc/{assetName}")
public class OhlcController {
    private static final Logger LOG = LoggerFactory.getLogger(OhlcController.class);

    private final UserService userService;
    private final ExchangeProvider exchangeProvider;
    private final ConversionService conversionService;

    public OhlcController(
            UserService userService,
            ExchangeProvider exchangeProvider,
            ConversionService conversionService
    ) {
        this.userService = userService;
        this.exchangeProvider = exchangeProvider;
        this.conversionService = conversionService;
    }

    @GetMapping
    public List<OhlcDto> list(Principal principal, @PathVariable String assetName, @RequestParam String interval, @RequestParam(value = "start", required = false) Long start, @RequestParam(value = "end", required = false) Long end) {
        throw new UnsupportedOperationException();
        //        return userService.findById(principal.getName())
//                .map(user -> {
//                    ExchangeService exchangeService = exchangeFactory.create(, ExchangeProvider.BINANCE);
//                    return exchangeService.listOhlc(assetName, interval, start, end)
//                            .stream()
//                            .map(this::toOhlcDto)
//                            .collect(Collectors.toList());
//                })
//                .orElseThrow(() -> new UserNotFoundException());
    }


    private OhlcDto toOhlcDto(Ohlc ohlc) {
        return conversionService.convert(ohlc, OhlcDto.class);
    }
}
