package com.coinbank.core.web.controllers;

import com.coinbank.core.domain.ApiKey;
import com.coinbank.core.services.ApiKeyService;
import com.coinbank.core.web.dto.ApiKeyDto;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/management")
public class ApiManagementController {
    private final ApiKeyService apiKeyService;
    private final ConversionService conversionService;

    public ApiManagementController(ApiKeyService apiKeyService, ConversionService conversionService) {
        this.apiKeyService = apiKeyService;
        this.conversionService = conversionService;
    }

    @PostMapping
    public void createApiKey(Principal principal, @RequestBody ApiKeyDto apiKeyDto) {
        ApiKey apiKey = this.toApiKey(apiKeyDto);
        apiKeyService.create(principal, apiKey);
    }

    @DeleteMapping("/{label}")
    public void deleteApiKey(Principal principal, @PathVariable String label) {
        apiKeyService.delete(principal, label);
    }

    @GetMapping
    public List<ApiKeyDto> listApiKey(Principal principal) {
        return apiKeyService.list(principal).stream()
                .map(this::toApiKeyDto)
                .collect(Collectors.toList());
    }

    private ApiKey toApiKey(ApiKeyDto apiKeyDto) {
        return conversionService.convert(apiKeyDto, ApiKey.class);
    }

    private ApiKeyDto toApiKeyDto(ApiKey apiKey) {
        return new ApiKeyDto(apiKey.getLabel(), null, null, null);
    }
}
