package com.jay;

public interface IMeasurable {

    double convertToBaseUnit(double value);

    double convertFromBaseUnit(double baseValue);

    String getUnitName();

    default void validateOperationSupport(ArithmeticOperation operation) {
        // default: allow arithmetic
    }
}