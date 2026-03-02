package com.jay;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class QuantityMeasurementAppTest {

    /* =========================
       Equality Tests
       ========================= */

    @Test
    void testEquality_SameFeet() {
        var q1 = new QuantityMeasurementApp(1.0, LengthUnit.FEET);
        var q2 = new QuantityMeasurementApp(1.0, LengthUnit.FEET);

        assertEquals(q1, q2);
    }

    @Test
    void testEquality_FeetToInches() {
        var feet = new QuantityMeasurementApp(1.0, LengthUnit.FEET);
        var inches = new QuantityMeasurementApp(12.0, LengthUnit.INCHES);

        assertEquals(feet, inches);
    }

    @Test
    void testEquality_YardsToFeet() {
        var yards = new QuantityMeasurementApp(1.0, LengthUnit.YARDS);
        var feet = new QuantityMeasurementApp(3.0, LengthUnit.FEET);

        assertEquals(yards, feet);
    }

    @Test
    void testEquality_DifferentValues() {
        var q1 = new QuantityMeasurementApp(1.0, LengthUnit.FEET);
        var q2 = new QuantityMeasurementApp(2.0, LengthUnit.FEET);

        assertNotEquals(q1, q2);
    }

    @Test
    void testEquality_NullComparison() {
        var q1 = new QuantityMeasurementApp(1.0, LengthUnit.FEET);

        assertNotEquals(null, q1);
    }

    @Test
    void testEquality_FeetToCentimeters() {
        var feet = new QuantityMeasurementApp(1.0, LengthUnit.FEET);
        var cm = new QuantityMeasurementApp(30.48, LengthUnit.CENTIMETERS);

        assertEquals(feet, cm);
    }

    /* =========================
       UC6 Addition Tests
       ========================= */

    @Test
    void testAddition_SameUnit_FeetPlusFeet() {
        var q1 = new QuantityMeasurementApp(1.0, LengthUnit.FEET);
        var q2 = new QuantityMeasurementApp(2.0, LengthUnit.FEET);

        var result = q1.add(q2);

        assertEquals(new QuantityMeasurementApp(3.0, LengthUnit.FEET), result);
    }

    @Test
    void testAddition_CrossUnit_FeetPlusInches() {
        var feet = new QuantityMeasurementApp(1.0, LengthUnit.FEET);
        var inches = new QuantityMeasurementApp(12.0, LengthUnit.INCHES);

        var result = feet.add(inches);

        assertEquals(new QuantityMeasurementApp(2.0, LengthUnit.FEET), result);
    }

    @Test
    void testAddition_Commutativity() {
        var feet = new QuantityMeasurementApp(1.0, LengthUnit.FEET);
        var inches = new QuantityMeasurementApp(12.0, LengthUnit.INCHES);

        assertEquals(feet.add(inches), inches.add(feet));
    }

    /* =========================
       UC7 Explicit Target Unit Tests
       ========================= */

    @Test
    void testAddition_ExplicitTargetUnit_Feet() {
        var a = new QuantityMeasurementApp(1.0, LengthUnit.FEET);
        var b = new QuantityMeasurementApp(12.0, LengthUnit.INCHES);

        assertEquals(new QuantityMeasurementApp(2.0, LengthUnit.FEET),
                a.add(b, LengthUnit.FEET));
    }

    @Test
    void testAddition_ExplicitTargetUnit_Inches() {
        var a = new QuantityMeasurementApp(1.0, LengthUnit.FEET);
        var b = new QuantityMeasurementApp(12.0, LengthUnit.INCHES);

        assertEquals(new QuantityMeasurementApp(24.0, LengthUnit.INCHES),
                a.add(b, LengthUnit.INCHES));
    }

    @Test
    void testAddition_ExplicitTargetUnit_Yards() {
        var a = new QuantityMeasurementApp(1.0, LengthUnit.FEET);
        var b = new QuantityMeasurementApp(12.0, LengthUnit.INCHES);

        var result = a.add(b, LengthUnit.YARDS);

        assertEquals(
                0.6667,
                result.getValue(),
                QuantityMeasurementApp.EPSILON
        );
    }

    @Test
    void testAddition_NullTargetUnit() {
        var a = new QuantityMeasurementApp(1.0, LengthUnit.FEET);
        var b = new QuantityMeasurementApp(12.0, LengthUnit.INCHES);

        assertThrows(IllegalArgumentException.class,
                () -> a.add(b, null));
    }
}