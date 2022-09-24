package ai.cryptocast.core.domain;

import ai.cryptocast.core.enums.Exchange;

import java.math.BigDecimal;

public class WalletBalance {
    private String name;
    private String fullName;
    private Exchange exchange;
    private BigDecimal totalFunds;
    private BigDecimal fundsAvailable;
    private BigDecimal usedInAnyOutstandingOrders;

    private WalletBalance(Builder builder) {
        setName(builder.name);
        setFullName(builder.fullName);
        setExchange(builder.exchange);
        setTotalFunds(builder.totalFunds);
        setFundsAvailable(builder.fundsAvailable);
        setUsedInAnyOutstandingOrders(builder.usedInAnyOutstandingOrders);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Exchange getExchange() {
        return exchange;
    }

    public void setExchange(Exchange exchange) {
        this.exchange = exchange;
    }

    public BigDecimal getTotalFunds() {
        return totalFunds;
    }

    public void setTotalFunds(BigDecimal totalFunds) {
        this.totalFunds = totalFunds;
    }

    public BigDecimal getFundsAvailable() {
        return fundsAvailable;
    }

    public void setFundsAvailable(BigDecimal fundsAvailable) {
        this.fundsAvailable = fundsAvailable;
    }

    public BigDecimal getUsedInAnyOutstandingOrders() {
        return usedInAnyOutstandingOrders;
    }

    public void setUsedInAnyOutstandingOrders(BigDecimal usedInAnyOutstandingOrders) {
        this.usedInAnyOutstandingOrders = usedInAnyOutstandingOrders;
    }

    public static final class Builder {
        private String name;
        private String fullName;
        private Exchange exchange;
        private BigDecimal totalFunds;
        private BigDecimal fundsAvailable;
        private BigDecimal usedInAnyOutstandingOrders;

        private Builder() {}

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder fullName(String fullName) {
            this.fullName = fullName;
            return this;
        }

        public Builder exchange(Exchange exchange) {
            this.exchange = exchange;
            return this;
        }

        public Builder totalFunds(BigDecimal totalFunds) {
            this.totalFunds = totalFunds;
            return this;
        }

        public Builder fundsAvailable(BigDecimal fundsAvailable) {
            this.fundsAvailable = fundsAvailable;
            return this;
        }

        public Builder usedInAnyOutstandingOrders(BigDecimal usedInAnyOutstandingOrders) {
            this.usedInAnyOutstandingOrders = usedInAnyOutstandingOrders;
            return this;
        }

        public WalletBalance build() {
            return new WalletBalance(this);
        }
    }
}

