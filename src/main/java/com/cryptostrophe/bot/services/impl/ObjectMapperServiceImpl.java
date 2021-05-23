package com.cryptostrophe.bot.services.impl;

import com.cryptostrophe.bot.exceptions.ObjectServiceSerializeException;
import com.cryptostrophe.bot.services.ObjectMapperService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class ObjectMapperServiceImpl implements ObjectMapperService {
    private final ObjectMapper objectMapper;

    public ObjectMapperServiceImpl(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public String serializeAsPrettyString(Object object) {
        try {
            return objectMapper.writer().withDefaultPrettyPrinter().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new ObjectServiceSerializeException(e.getMessage());
        }
    }
}
