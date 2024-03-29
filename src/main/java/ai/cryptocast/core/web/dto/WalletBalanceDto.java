package ai.cryptocast.core.web.dto;

import ai.cryptocast.core.enums.Exchange;
import com.fasterxml.jackson.annotation.JsonCreator;

import java.math.BigDecimal;

public class WalletBalanceDto {
    private final String name;
    private final String fullName;
    private final Exchange exchange;
    private final BigDecimal totalFunds;
    private final BigDecimal fundsAvailable;
    private final BigDecimal usedInAnyOutstandingOrders;

    @JsonCreator
    public WalletBalanceDto(
            String name,
            String fullName,
            Exchange exchange,
            BigDecimal totalFunds,
            BigDecimal fundsAvailable,
            BigDecimal usedInAnyOutstandingOrders
    ) {
        this.name = name;
        this.fullName = fullName;
        this.exchange = exchange;
        this.totalFunds = totalFunds;
        this.fundsAvailable = fundsAvailable;
        this.usedInAnyOutstandingOrders = usedInAnyOutstandingOrders;
    }

    public String getName() {
        return name;
    }

    public String getFullName() {
        return fullName;
    }

    public Exchange getExchange() {
        return exchange;
    }

    public BigDecimal getTotalFunds() {
        return totalFunds;
    }

    public BigDecimal getFundsAvailable() {
        return fundsAvailable;
    }

    public BigDecimal getUsedInAnyOutstandingOrders() {
        return usedInAnyOutstandingOrders;
    }
}