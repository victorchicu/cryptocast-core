package com.crypto.core.aggregators.services;

import com.crypto.core.aggregators.dto.SymbolDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.security.Principal;

public interface SymbolAggregatorService {
    Page<SymbolDto> listSupportedSymbols(Principal principal, Pageable pageable);
}
