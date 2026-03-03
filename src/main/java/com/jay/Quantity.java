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

    /* =========================
       Equality
       ========================= */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Quantity<?> other)) return false;

        if (!this.unit.getClass().equals(other.unit.getClass()))
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

    /* =========================
       Conversion
       ========================= */
    public Quantity<U> convertTo(U targetUnit) {
        if (targetUnit == null)
            throw new IllegalArgumentException("Target unit cannot be null");

        double baseValue = unit.convertToBaseUnit(value);
        double convertedValue = targetUnit.convertFromBaseUnit(baseValue);

        return new Quantity<>(convertedValue, targetUnit);
    }

    /* =========================
       Addition
       ========================= */
    public Quantity<U> add(Quantity<U> other) {
        return add(other, this.unit);
    }

    public Quantity<U> add(Quantity<U> other, U targetUnit) {

        validateArithmeticOperation(other, targetUnit);

        double resultBase =
                unit.convertToBaseUnit(value) +
                        other.unit.convertToBaseUnit(other.value);

        double converted = targetUnit.convertFromBaseUnit(resultBase);

        return new Quantity<>(converted, targetUnit);
    }

    /* =========================
       Subtraction (UC12)
       ========================= */
    public Quantity<U> subtract(Quantity<U> other) {
        return subtract(other, this.unit);
    }

    public Quantity<U> subtract(Quantity<U> other, U targetUnit) {

        validateArithmeticOperation(other, targetUnit);

        double resultBase =
                unit.convertToBaseUnit(value) -
                        other.unit.convertToBaseUnit(other.value);

        double converted = targetUnit.convertFromBaseUnit(resultBase);

        // round to 2 decimal places
        converted = Math.round(converted * 100.0) / 100.0;

        return new Quantity<>(converted, targetUnit);
    }

    /* =========================
       Division (UC12)
       ========================= */
    public double divide(Quantity<U> other) {

        if (other == null)
            throw new IllegalArgumentException("Other quantity cannot be null");

        if (!this.unit.getClass().equals(other.unit.getClass()))
            throw new IllegalArgumentException("Cannot divide different measurement categories");

        double thisBase = unit.convertToBaseUnit(value);
        double otherBase = other.unit.convertToBaseUnit(other.value);

        if (otherBase == 0.0)
            throw new ArithmeticException("Division by zero");

        return thisBase / otherBase;
    }

    /* =========================
       Shared Validation
       ========================= */
    private void validateArithmeticOperation(Quantity<U> other, U targetUnit) {

        if (other == null)
            throw new IllegalArgumentException("Other quantity cannot be null");

        if (targetUnit == null)
            throw new IllegalArgumentException("Target unit cannot be null");

        if (!this.unit.getClass().equals(other.unit.getClass()))
            throw new IllegalArgumentException("Cannot operate on different measurement categories");
    }

    @Override
    public String toString() {
        return "Quantity(" + value + ", " + unit.getUnitName() + ")";
    }
}