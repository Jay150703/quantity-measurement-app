package com.jay;

public enum WeightUnit {

    KILOGRAM(1.0),        // Base unit
    GRAM(0.001),          // 1 g = 0.001 kg
    POUND(0.453592);      // 1 lb ≈ 0.453592 kg

    private final double conversionFactorToBase;

    WeightUnit(double conversionFactorToBase) {
        this.conversionFactorToBase = conversionFactorToBase;
    }

    public double getConversionFactor() {
        return conversionFactorToBase;
    }

    // Convert this unit to base unit (kilogram)
    public double convertToBaseUnit(double value) {
        return value * conversionFactorToBase;
    }

    // Convert from base unit (kilogram) to this unit
    public double convertFromBaseUnit(double baseValue) {
        return baseValue / conversionFactorToBase;
    }
}