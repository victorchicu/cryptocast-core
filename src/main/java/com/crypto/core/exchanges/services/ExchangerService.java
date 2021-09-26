package com.crypto.core.exchanges.services;

import com.crypto.core.exchanges.dto.SymbolDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.security.Principal;

public interface ExchangerService {
    Page<SymbolDto> listSymbols(Principal principal, Pageable pageable);
}
