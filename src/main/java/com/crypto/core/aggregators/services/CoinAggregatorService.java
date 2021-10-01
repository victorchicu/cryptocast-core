package com.crypto.core.aggregators.services;

import com.crypto.core.aggregators.dto.CoinDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.security.Principal;

public interface CoinAggregatorService {
    Page<CoinDto> listSupportedCoins(Principal principal, Pageable pageable);
}
