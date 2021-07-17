package com.crypto.cli.freemarker.utils;

import java.math.BigDecimal;

public class NumberUtils {
    public String divide(BigDecimal dividend, BigDecimal divisor) {
        BigDecimal decimal = dividend.divide(divisor);
        return decimal.toPlainString();
    }

    public String toPlainString(BigDecimal value) {
        return value.toPlainString();
    }
}
