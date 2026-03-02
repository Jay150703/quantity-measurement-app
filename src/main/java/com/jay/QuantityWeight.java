package com.jay;

import java.util.Objects;

public class QuantityWeight {

    private static final double EPSILON = 0.0001;

    private final double value;
    private final WeightUnit unit;

    public QuantityWeight(double value, WeightUnit unit) {
        if (unit == null) {
            throw new IllegalArgumentException("Weight unit cannot be null");
        }
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Invalid numeric value");
        }
        this.value = value;
        this.unit = unit;
    }

    public double getValue() {
        return value;
    }

    public WeightUnit getUnit() {
        return unit;
    }

    /* =========================
       Equality (Base Normalization)
       ========================= */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || this.getClass() != obj.getClass()) return false;

        QuantityWeight other = (QuantityWeight) obj;

        double thisBase = this.unit.convertToBaseUnit(this.value);
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
    public QuantityWeight convertTo(WeightUnit targetUnit) {
        if (targetUnit == null) {
            throw new IllegalArgumentException("Target unit cannot be null");
        }

        double baseValue = unit.convertToBaseUnit(value);
        double convertedValue = targetUnit.convertFromBaseUnit(baseValue);

        return new QuantityWeight(convertedValue, targetUnit);
    }

    /* =========================
       Addition (Default → First Operand Unit)
       ========================= */
    public QuantityWeight add(QuantityWeight other) {
        return add(other, this.unit);
    }

    /* =========================
       Addition (Explicit Target Unit)
       ========================= */
    public QuantityWeight add(QuantityWeight other, WeightUnit targetUnit) {
        if (other == null) {
            throw new IllegalArgumentException("Second operand cannot be null");
        }
        if (targetUnit == null) {
            throw new IllegalArgumentException("Target unit cannot be null");
        }

        double sumBase = this.unit.convertToBaseUnit(this.value)
                + other.unit.convertToBaseUnit(other.value);

        double result = targetUnit.convertFromBaseUnit(sumBase);

        return new QuantityWeight(result, targetUnit);
    }

    @Override
    public String toString() {
        return "Quantity(" + value + ", " + unit + ")";
    }
}