package com.jay;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class QuantityMeasurementAppTest {

    @Test
    void testEquality_SameFeet() {
        var q1 = new QuantityMeasurementApp.QuantityLength(1.0,
                QuantityMeasurementApp.LengthUnit.FEET);
        var q2 = new QuantityMeasurementApp.QuantityLength(1.0,
                QuantityMeasurementApp.LengthUnit.FEET);

        assertTrue(q1.equals(q2));
    }

    @Test
    void testEquality_FeetToInches() {
        var feet = new QuantityMeasurementApp.QuantityLength(1.0,
                QuantityMeasurementApp.LengthUnit.FEET);
        var inches = new QuantityMeasurementApp.QuantityLength(12.0,
                QuantityMeasurementApp.LengthUnit.INCHES);

        assertTrue(feet.equals(inches));
    }

    @Test
    void testEquality_YardsToFeet() {
        var yards = new QuantityMeasurementApp.QuantityLength(1.0,
                QuantityMeasurementApp.LengthUnit.YARDS);
        var feet = new QuantityMeasurementApp.QuantityLength(3.0,
                QuantityMeasurementApp.LengthUnit.FEET);

        assertTrue(yards.equals(feet));
    }

    @Test
    void testEquality_DifferentValues() {
        var q1 = new QuantityMeasurementApp.QuantityLength(1.0,
                QuantityMeasurementApp.LengthUnit.FEET);
        var q2 = new QuantityMeasurementApp.QuantityLength(2.0,
                QuantityMeasurementApp.LengthUnit.FEET);

        assertFalse(q1.equals(q2));
    }

    @Test
    void testEquality_NullComparison() {
        var q1 = new QuantityMeasurementApp.QuantityLength(1.0,
                QuantityMeasurementApp.LengthUnit.FEET);

        assertFalse(q1.equals(null));
    }

    @Test
    void testEquality_FeetToCentimeters() {
        var feet = new QuantityMeasurementApp.QuantityLength(1.0,
                QuantityMeasurementApp.LengthUnit.FEET);
        var cm = new QuantityMeasurementApp.QuantityLength(30.48,
                QuantityMeasurementApp.LengthUnit.CENTIMETERS);

        assertTrue(feet.equals(cm));
    }
    @Test
    void testAddition_SameUnit_FeetPlusFeet() {
        var q1 = new QuantityMeasurementApp.QuantityLength(1.0,
                QuantityMeasurementApp.LengthUnit.FEET);
        var q2 = new QuantityMeasurementApp.QuantityLength(2.0,
                QuantityMeasurementApp.LengthUnit.FEET);

        var result = q1.add(q2);

        assertEquals(
                new QuantityMeasurementApp.QuantityLength(3.0,
                        QuantityMeasurementApp.LengthUnit.FEET),
                result
        );
    }

    @Test
    void testAddition_CrossUnit_FeetPlusInches() {
        var feet = new QuantityMeasurementApp.QuantityLength(1.0,
                QuantityMeasurementApp.LengthUnit.FEET);
        var inches = new QuantityMeasurementApp.QuantityLength(12.0,
                QuantityMeasurementApp.LengthUnit.INCHES);

        var result = feet.add(inches);

        assertEquals(
                new QuantityMeasurementApp.QuantityLength(2.0,
                        QuantityMeasurementApp.LengthUnit.FEET),
                result
        );
    }

    @Test
    void testAddition_Commutativity() {
        var feet = new QuantityMeasurementApp.QuantityLength(1.0,
                QuantityMeasurementApp.LengthUnit.FEET);
        var inches = new QuantityMeasurementApp.QuantityLength(12.0,
                QuantityMeasurementApp.LengthUnit.INCHES);

        assertEquals(feet.add(inches), inches.add(feet));
    }
}