package com.jay;

import java.util.Objects;

public class Quantity<U extends IMeasurable> {

    private static final double EPSILON = 0.0001;

    private final double value;
    private final U unit;

    public Quantity(double value, U unit) {

        if (unit == null)
            throw new IllegalArgumentException("Unit cannot be null");

        if (!Double.isFinite(value))
            throw new IllegalArgumentException("Invalid numeric value");

        this.value = value;
        this.unit = unit;
    }

    public double getValue() {
        return value;
    }

    public U getUnit() {
        return unit;
    }

    /* =====================================================
       Equality
       ===================================================== */

    @Override
    public boolean equals(Object obj) {

        if (this == obj) return true;
        if (!(obj instanceof Quantity<?> other)) return false;

        if (this.unit.getClass() != other.unit.getClass())
            return false;

        double thisBase = unit.convertToBaseUnit(value);
        double otherBase = other.unit.convertToBaseUnit(other.value);

        return Math.abs(thisBase - otherBase) < EPSILON;
    }

    @Override
    public int hashCode() {
        double baseValue = unit.convertToBaseUnit(value);
        return Objects.hash(Math.round(baseValue / EPSILON));
    }

    /* =====================================================
       Conversion
       ===================================================== */

    public Quantity<U> convertTo(U targetUnit) {

        if (targetUnit == null)
            throw new IllegalArgumentException("Target unit cannot be null");

        double baseValue = unit.convertToBaseUnit(value);
        double converted = targetUnit.convertFromBaseUnit(baseValue);

        return new Quantity<>(converted, targetUnit);
    }

    /* =====================================================
       Addition
       ===================================================== */

    public Quantity<U> add(Quantity<U> other) {
        return add(other, this.unit);
    }

    public Quantity<U> add(Quantity<U> other, U targetUnit) {

        validateArithmeticOperands(other, targetUnit, true);

        unit.validateOperationSupport(ArithmeticOperation.ADD);

        double resultBase =
                performBaseArithmetic(other, ArithmeticOperation.ADD);

        double converted =
                targetUnit.convertFromBaseUnit(resultBase);

        converted = roundToTwoDecimals(converted);

        return new Quantity<>(converted, targetUnit);
    }

    /* =====================================================
       Subtraction
       ===================================================== */

    public Quantity<U> subtract(Quantity<U> other) {
        return subtract(other, this.unit);
    }

    public Quantity<U> subtract(Quantity<U> other, U targetUnit) {

        validateArithmeticOperands(other, targetUnit, true);

        unit.validateOperationSupport(ArithmeticOperation.SUBTRACT);

        double resultBase =
                performBaseArithmetic(other, ArithmeticOperation.SUBTRACT);

        double converted =
                targetUnit.convertFromBaseUnit(resultBase);

        converted = roundToTwoDecimals(converted);

        return new Quantity<>(converted, targetUnit);
    }

    /* =====================================================
       Division
       ===================================================== */

    public double divide(Quantity<U> other) {

        validateArithmeticOperands(other, null, false);

        unit.validateOperationSupport(ArithmeticOperation.DIVIDE);

        return performBaseArithmetic(other, ArithmeticOperation.DIVIDE);
    }

    /* =====================================================
       Centralized Arithmetic
       ===================================================== */

    private double performBaseArithmetic(
            Quantity<U> other,
            ArithmeticOperation operation) {

        double thisBase = unit.convertToBaseUnit(value);
        double otherBase = other.unit.convertToBaseUnit(other.value);

        return operation.apply(thisBase, otherBase);
    }

    private void validateArithmeticOperands(
            Quantity<U> other,
            U targetUnit,
            boolean targetRequired) {

        if (other == null)
            throw new IllegalArgumentException("Other quantity cannot be null");

        if (this.unit.getClass() != other.unit.getClass())
            throw new IllegalArgumentException("Incompatible unit categories");

        if (!Double.isFinite(other.value))
            throw new IllegalArgumentException("Invalid numeric value");

        if (targetRequired && targetUnit == null)
            throw new IllegalArgumentException("Target unit cannot be null");
    }

    private double roundToTwoDecimals(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    @Override
    public String toString() {
        return "Quantity(" + value + ", " + unit.getUnitName() + ")";
    }
}