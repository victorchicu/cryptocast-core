package com.cryptostrophe.bot.configs;

import java.math.BigDecimal;

public class Cryptocurrency {
    private String name;
    private BigDecimal divisor;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getDivisor() {
        return divisor;
    }

    public void setDivisor(BigDecimal divisor) {
        this.divisor = divisor;
    }
}
