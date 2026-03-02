package com.jay;

public class QuantityMeasurementApp {

    public static final double EPSILON = 0.0001;

    private final double value;
    private final LengthUnit unit;

    /* =========================
       Constructor
       ========================= */
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
       UC5 – Equality (epsilon based)
       ========================= */
    @Override
    public boolean equals(Object obj) {

        if (this == obj) return true;
        if (!(obj instanceof QuantityMeasurementApp)) return false;

        QuantityMeasurementApp other = (QuantityMeasurementApp) obj;

        double thisBase = this.unit.toBase(this.value);
        double otherBase = other.unit.toBase(other.value);

        return Math.abs(thisBase - otherBase) < EPSILON;
    }

    @Override
    public int hashCode() {
        double baseValue = unit.toBase(value);
        return Double.hashCode(baseValue);
    }

    /* =========================
       UC6 – Addition (implicit unit)
       Result in first operand unit
       ========================= */
    public QuantityMeasurementApp add(QuantityMeasurementApp other) {
        return add(other, this.unit);
    }

    /* =========================
       UC7 – Addition (explicit target unit)
       ========================= */
    public QuantityMeasurementApp add(QuantityMeasurementApp other, LengthUnit targetUnit) {

        if (other == null) {
            throw new IllegalArgumentException("Second operand cannot be null");
        }

        if (targetUnit == null) {
            throw new IllegalArgumentException("Target unit cannot be null");
        }

        double result = addInBaseAndConvert(other, targetUnit);

        return new QuantityMeasurementApp(result, targetUnit);
    }

    /* =========================
       Private Utility Method
       (DRY principle)
       ========================= */
    private double addInBaseAndConvert(QuantityMeasurementApp other, LengthUnit targetUnit) {

        double thisBase = this.unit.toBase(this.value);
        double otherBase = other.unit.toBase(other.value);

        double sumBase = thisBase + otherBase;

        return targetUnit.fromBase(sumBase);
    }

    /* =========================
       Convert to another unit
       (UC5 compatibility)
       ========================= */
    public QuantityMeasurementApp convertTo(LengthUnit targetUnit) {

        if (targetUnit == null) {
            throw new IllegalArgumentException("Target unit cannot be null");
        }

        double baseValue = this.unit.toBase(this.value);
        double converted = targetUnit.fromBase(baseValue);

        return new QuantityMeasurementApp(converted, targetUnit);
    }

    @Override
    public String toString() {
        return "Quantity(" + value + ", " + unit + ")";
    }
}