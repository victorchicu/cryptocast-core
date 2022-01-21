package com.trader.core.enums;

public enum Quotation {
    FALL(-1),
    EQUAL(0),
    RISE(1);

    private final int value;

    Quotation(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static Quotation valueOf(int value) {
        switch (value) {
            case -1:
                return FALL;
            case 1:
                return RISE;
            default:
                return EQUAL;
        }
    }
}
