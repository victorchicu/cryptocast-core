package ai.cryptocast.core.web.dto;

import ai.cryptocast.core.enums.ExchangeType;
import com.fasterxml.jackson.annotation.JsonCreator;

import java.math.BigDecimal;

public record WalletBalanceDto(
        String name,
        String fullName,
        ExchangeType exchange,
        BigDecimal totalFunds,
        BigDecimal fundsAvailable,
        BigDecimal usedInAnyOutstandingOrders
) {
    @JsonCreator
    public WalletBalanceDto {
        //default constructor
    }
}