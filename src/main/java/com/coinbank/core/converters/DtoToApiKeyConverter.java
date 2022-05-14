package com.coinbank.core.converters;

import com.coinbank.core.domain.ApiKey;
import com.coinbank.core.dto.ApiKeyDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DtoToApiKeyConverter implements Converter<ApiKeyDto, ApiKey> {
    @Override
    public ApiKey convert(ApiKeyDto source) {
        return ApiKey.newBuilder()
                .label(source.getLabel())
                .apiKey(source.getApiKey())
                .secretKey(source.getSecretKey())
                .exchange(source.getExchange())
                .build();
    }
}
