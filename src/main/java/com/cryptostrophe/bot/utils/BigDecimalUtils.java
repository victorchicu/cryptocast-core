package com.cryptostrophe.bot.utils;

import java.math.BigDecimal;

public class BigDecimalUtils {
    public String divide(BigDecimal dividend, BigDecimal divisor) {
        BigDecimal decimal = dividend.divide(divisor);
        return decimal.toPlainString();
    }
}
