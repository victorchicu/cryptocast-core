package com.coinbank.core.web.controllers;

import com.coinbank.core.domain.ApiKey;
import com.coinbank.core.web.dto.ApiKeyDto;
import com.coinbank.core.domain.services.ApiConnectionService;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/connections")
public class ApiConnectionsController {
    private final ConversionService conversionService;
    private final ApiConnectionService apiConnectionService;

    public ApiConnectionsController(ConversionService conversionService, ApiConnectionService apiConnectionService) {
        this.conversionService = conversionService;
        this.apiConnectionService = apiConnectionService;
    }

    @PostMapping
    public void connectExchange(Principal principal, @RequestBody ApiKeyDto apiKeyDto) {
        ApiKey apiKey = this.toApiKey(apiKeyDto);
        apiConnectionService.create(principal, apiKey);
    }

    @DeleteMapping("/{label}")
    public void disconnectExchange(Principal principal, @PathVariable String label) {
        apiConnectionService.delete(principal, label);
    }


    private ApiKey toApiKey(ApiKeyDto apiKeyDto) {
        return conversionService.convert(apiKeyDto, ApiKey.class);
    }
}
