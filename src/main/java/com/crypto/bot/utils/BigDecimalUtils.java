package com.crypto.bot.utils;

import java.math.BigDecimal;

public class BigDecimalUtils {
    public static BigDecimal findPercentChangeBetweenTwoNumbers(BigDecimal a, BigDecimal b) {
        if (a.compareTo(b) < 0) {
            return ((b.subtract(a)).divide(a, BigDecimal.ROUND_UP)).multiply(BigDecimal.valueOf(100));
        } else if (a.compareTo(b) > 0) {
            return ((a.subtract(b)).divide(a, BigDecimal.ROUND_UP)).multiply(BigDecimal.valueOf(100));
        } else {
            return BigDecimal.ZERO;
        }
    }
}
