package com.coinbank.core.web.controllers;

import com.coinbank.core.domain.ApiKey;
import com.coinbank.core.domain.services.ExchangeConnectService;
import com.coinbank.core.web.dto.ApiKeyDto;
import com.coinbank.core.web.dto.ExchangeDto;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/exchanges")
public class ExchangesController {
    private final ConversionService conversionService;
    private final ExchangeConnectService exchangeConnectService;

    public ExchangesController(ConversionService conversionService, ExchangeConnectService exchangeConnectService) {
        this.conversionService = conversionService;
        this.exchangeConnectService = exchangeConnectService;
    }

    @PostMapping
    public void createExchange(Principal principal, @RequestBody ApiKeyDto apiKeyDto) {
        ApiKey apiKey = this.toApiKey(apiKeyDto);
        exchangeConnectService.create(principal, apiKey);
    }

    @DeleteMapping("/{label}")
    public void deleteExchange(Principal principal, @PathVariable String label) {
        exchangeConnectService.delete(principal, label);
    }

    @GetMapping
    public List<ExchangeDto> listExchanges(Principal principal) {
        return exchangeConnectService.list(principal).stream()
                .map(this::toExchangeDto)
                .collect(Collectors.toList());
    }


    private ApiKey toApiKey(ApiKeyDto apiKeyDto) {
        return conversionService.convert(apiKeyDto, ApiKey.class);
    }

    private ExchangeDto toExchangeDto(ApiKey apiKey) {
        return conversionService.convert(apiKey, ExchangeDto.class);
    }
}
