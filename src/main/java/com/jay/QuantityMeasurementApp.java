package com.jay;

public class QuantityMeasurementApp {

    public static final double EPSILON = 0.0001;

    private final double value;
    private final LengthUnit unit;

    public QuantityMeasurementApp(double value, LengthUnit unit) {

        if (unit == null) {
            throw new IllegalArgumentException("Unit cannot be null");
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

    public LengthUnit getUnit() {
        return unit;
    }

    /* =========================
       Equality (delegates to unit)
       ========================= */
    @Override
    public boolean equals(Object obj) {

        if (this == obj) return true;
        if (!(obj instanceof QuantityMeasurementApp)) return false;

        QuantityMeasurementApp other = (QuantityMeasurementApp) obj;

        double thisBase = this.unit.convertToBaseUnit(this.value);
        double otherBase = other.unit.convertToBaseUnit(other.value);

        return Math.abs(thisBase - otherBase) < EPSILON;
    }

    @Override
    public int hashCode() {
        double baseValue = unit.convertToBaseUnit(value);
        return Double.hashCode(baseValue);
    }

    /* =========================
       Convert to another unit
       ========================= */
    public QuantityMeasurementApp convertTo(LengthUnit targetUnit) {

        if (targetUnit == null) {
            throw new IllegalArgumentException("Target unit cannot be null");
        }

        double baseValue = this.unit.convertToBaseUnit(this.value);
        double converted = targetUnit.convertFromBaseUnit(baseValue);

        return new QuantityMeasurementApp(converted, targetUnit);
    }

    /* =========================
       UC6 – Implicit addition
       ========================= */
    public QuantityMeasurementApp add(QuantityMeasurementApp other) {
        return add(other, this.unit);
    }

    /* =========================
       UC7 – Explicit target unit
       ========================= */
    public QuantityMeasurementApp add(QuantityMeasurementApp other, LengthUnit targetUnit) {

        if (other == null) {
            throw new IllegalArgumentException("Second operand cannot be null");
        }

        if (targetUnit == null) {
            throw new IllegalArgumentException("Target unit cannot be null");
        }

        double thisBase = this.unit.convertToBaseUnit(this.value);
        double otherBase = other.unit.convertToBaseUnit(other.value);

        double sumBase = thisBase + otherBase;

        double result = targetUnit.convertFromBaseUnit(sumBase);

        return new QuantityMeasurementApp(result, targetUnit);
    }

    @Override
    public String toString() {
        return "Quantity(" + value + ", " + unit + ")";
    }
}