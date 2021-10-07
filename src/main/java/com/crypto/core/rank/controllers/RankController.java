package com.crypto.core.rank.controllers;

import com.crypto.core.rank.dto.SymbolDto;
import com.crypto.core.rank.services.RankService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/rank")
public class RankController {
    private final RankService rankService;

    public RankController(RankService rankService) {
        this.rankService = rankService;
    }

    @GetMapping
    public Page<SymbolDto> listSupportedSymbols(Principal principal, Pageable pageable) {
        return rankService.listSupportedSymbols(principal, pageable);
    }
}
