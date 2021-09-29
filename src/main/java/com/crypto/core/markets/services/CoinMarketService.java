package com.crypto.core.markets.services;

import com.crypto.core.markets.dto.CoinDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.security.Principal;

public interface CoinMarketService {
    Page<CoinDto> listSupportedCoins(Principal principal, Pageable pageable);
}
