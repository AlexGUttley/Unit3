package org.example.dto;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * The enum Change. Contains every common denomination of note and change used by the british pound.
 */
public enum Change {
    FIFTY_POUND  (50,   "£50 note"),
    TWENTY_POUND (20,   "£20 note"),
    TEN_POUND    (10,   "£10 note"),
    FIVE_POUND   (5,    "£5 note"),
    TWO_POUND    (2,    "£2 coin"),
    ONE_POUND    (1,    "£1 coin"),
    FIFTY_PENCE  (0.50, "50p coin"),
    TWENTY_PENCE (0.20, "20p coin"),
    TEN_PENCE    (0.10, "10p coin"),
    FIVE_PENCE   (0.05, "5p coin"),
    TWO_PENCE    (0.02, "2p coin"),
    ONE_PENNY    (0.01, "1p coin");

    private final BigDecimal value;
    private final String valueName;

    Change(int value, String valueName) {
        this.value = new BigDecimal(value);
        this.valueName = valueName;
    }

    Change(double value, String valueName) {
        this.value = new BigDecimal(value, new MathContext(3));
        this.valueName = valueName;
    }

    /**
     * Gets value.
     *
     * @return the value
     */
    public BigDecimal getValue() {
        return value;
    }

    /**
     * Gets value name.
     *
     * @return the value name
     */
    public String getValueName() {
        return valueName;
    }
}
