package com.coinbank.core.web.converters;

import com.coinbank.core.domain.ApiKey;
import com.coinbank.core.web.dto.ExchangeDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ApiKeyToExchangeDtoConverter implements Converter<ApiKey, ExchangeDto> {
    @Override
    public ExchangeDto convert(ApiKey apiKey) {
        return new ExchangeDto(apiKey.getLabel());
    }
}
