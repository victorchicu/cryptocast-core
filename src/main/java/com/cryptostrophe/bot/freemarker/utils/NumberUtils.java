package com.cryptostrophe.bot.freemarker.utils;

import java.math.BigDecimal;

public class NumberUtils {
    public String divide(BigDecimal dividend, BigDecimal divisor) {
        BigDecimal decimal = dividend.divide(divisor);
        return decimal.toPlainString();
    }
}
