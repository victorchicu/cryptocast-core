package ai.cryptocast.core.web.controllers;

import ai.cryptocast.core.domain.Wallet;
import ai.cryptocast.core.services.WalletService;
import ai.cryptocast.core.web.dto.WalletDto;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/wallets")
public class WalletsController {
    private final WalletService walletService;
    private final ConversionService conversionService;

    public WalletsController(WalletService walletService, ConversionService conversionService) {
        this.walletService = walletService;
        this.conversionService = conversionService;
    }

    @PostMapping
    public void createWallet(Principal principal, @RequestBody WalletDto walletDto) {
        Wallet wallet = this.toWallet(walletDto);
        walletService.create(principal, wallet);
    }

    @DeleteMapping("/{label}")
    public void deleteWallet(Principal principal, @PathVariable String label) {
        walletService.delete(principal, label);
    }

    @GetMapping
    public List<WalletDto> listWallets(Principal principal) {
        return walletService.list(principal).stream()
                .map(this::toWalletDto)
                .collect(Collectors.toList());
    }

    private Wallet toWallet(WalletDto walletDto) {
        return conversionService.convert(walletDto, Wallet.class);
    }

    private WalletDto toWalletDto(Wallet wallet) {
        return new WalletDto(wallet.getLabel(), null, null, null);
    }
}
