package com.trader.core.controllers;

import com.trader.core.domain.FundsBalance;
import com.trader.core.dto.ChipDto;
import com.trader.core.dto.FundsBalanceDto;
import com.trader.core.exceptions.UserNotFoundException;
import com.trader.core.services.ExchangeService;
import com.trader.core.services.ExchangeStrategy;
import com.trader.core.services.FundsService;
import com.trader.core.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/funds")
public class FundsController {
    private static final Logger LOG = LoggerFactory.getLogger(FundsController.class);

    private final UserService userService;
    private final FundsService fundsService;
    private final ExchangeStrategy exchangeStrategy;
    private final ConversionService conversionService;

    public FundsController(
            UserService userService,
            FundsService fundsService,
            ExchangeStrategy exchangeStrategy,
            ConversionService conversionService
    ) {
        this.userService = userService;
        this.fundsService = fundsService;
        this.exchangeStrategy = exchangeStrategy;
        this.conversionService = conversionService;
    }

    @GetMapping
    public List<FundsBalanceDto> listFundsBalances(Principal principal) {
        return userService.findById(principal.getName())
                .map(user ->
                        fundsService.listFundsBalances(user).stream()
                                .map(this::toFundsBalanceDto)
                                .collect(Collectors.toList())
                )
                .orElseThrow(UserNotFoundException::new);
    }

    @GetMapping("/available")
    public List<ChipDto> availableFunds(Principal principal) {
        return userService.findById(principal.getName())
                .map(user -> {
                    ExchangeService exchangeService = exchangeStrategy.getExchangeService(user.getExchangeProvider());
                    return exchangeService.listSymbols();
                })
                .map((Set<String> symbols) -> symbols.stream()
                        .map(ChipDto::new)
                        .collect(Collectors.toList())
                )
                .orElseThrow(UserNotFoundException::new);
    }

    private FundsBalanceDto toFundsBalanceDto(FundsBalance fundsBalance) {
        return conversionService.convert(fundsBalance, FundsBalanceDto.class);
    }
}
