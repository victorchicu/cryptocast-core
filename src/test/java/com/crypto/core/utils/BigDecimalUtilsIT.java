package com.crypto.core.utils;

import com.crypto.core.utils.numbers.BigDecimalUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class BigDecimalUtilsIT {
    @Test
    public void should_compute_percent_difference_between_two_numbers_up() {
        BigDecimal diff = BigDecimalUtils.findPercentChangeBetweenTwoNumbers(
                BigDecimal.valueOf(500.0),
                BigDecimal.valueOf(150.0)
        );
        Assertions.assertFalse(diff.compareTo(BigDecimal.ZERO) == 0);
    }

    @Test
    public void should_compute_percent_difference_between_two_numbers_down() {
        BigDecimal diff = BigDecimalUtils.findPercentChangeBetweenTwoNumbers(
                BigDecimal.valueOf(150.0),
                BigDecimal.valueOf(500.0)
        );
        Assertions.assertFalse(diff.compareTo(BigDecimal.ZERO) == 0);
    }
}
