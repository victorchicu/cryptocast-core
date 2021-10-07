package com.crypto.core.rank.services;

import com.crypto.core.rank.dto.SymbolDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.security.Principal;

public interface RankService {
    Page<SymbolDto> listSupportedSymbols(Principal principal, Pageable pageable);
}
