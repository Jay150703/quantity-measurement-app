package com.jay;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class QuantityTest {

    private static final double EPSILON = 0.0001;

    /* =====================================================
       LENGTH TESTS
       ===================================================== */

    @Test
    void testLengthEquality_FeetToInches() {
        var feet = new Quantity<>(1.0, LengthUnit.FEET);
        var inches = new Quantity<>(12.0, LengthUnit.INCHES);

        assertEquals(feet, inches);
    }

    @Test
    void testLengthAddition_CrossUnit() {
        var feet = new Quantity<>(1.0, LengthUnit.FEET);
        var inches = new Quantity<>(12.0, LengthUnit.INCHES);

        assertEquals(2.0,
                feet.add(inches, LengthUnit.FEET).getValue(),
                EPSILON);
    }

    @Test
    void testLengthSubtraction_CrossUnit() {
        var feet = new Quantity<>(10.0, LengthUnit.FEET);
        var inches = new Quantity<>(6.0, LengthUnit.INCHES);

        assertEquals(9.5,
                feet.subtract(inches).getValue(),
                EPSILON);
    }

    @Test
    void testLengthDivision_CrossUnit() {
        var inches = new Quantity<>(24.0, LengthUnit.INCHES);
        var feet = new Quantity<>(2.0, LengthUnit.FEET);

        assertEquals(1.0, inches.divide(feet), EPSILON);
    }

    /* =====================================================
       WEIGHT TESTS
       ===================================================== */

    @Test
    void testWeightEquality_KgToGram() {
        var kg = new Quantity<>(1.0, WeightUnit.KILOGRAM);
        var gram = new Quantity<>(1000.0, WeightUnit.GRAM);

        assertEquals(kg, gram);
    }

    @Test
    void testWeightSubtraction() {
        var kg = new Quantity<>(10.0, WeightUnit.KILOGRAM);
        var gram = new Quantity<>(5000.0, WeightUnit.GRAM);

        assertEquals(5.0,
                kg.subtract(gram).getValue(),
                EPSILON);
    }

    @Test
    void testWeightDivision() {
        var kg = new Quantity<>(10.0, WeightUnit.KILOGRAM);
        var kg2 = new Quantity<>(5.0, WeightUnit.KILOGRAM);

        assertEquals(2.0, kg.divide(kg2), EPSILON);
    }

    /* =====================================================
       VOLUME TESTS
       ===================================================== */

    @Test
    void testVolumeEquality_LitreToMillilitre() {
        var litre = new Quantity<>(1.0, VolumeUnit.LITRE);
        var ml = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);

        assertEquals(litre, ml);
    }

    @Test
    void testVolumeAddition() {
        var litre = new Quantity<>(1.0, VolumeUnit.LITRE);
        var ml = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);

        assertEquals(2.0,
                litre.add(ml).getValue(),
                EPSILON);
    }

    @Test
    void testVolumeSubtraction() {
        var litre = new Quantity<>(5.0, VolumeUnit.LITRE);
        var ml = new Quantity<>(500.0, VolumeUnit.MILLILITRE);

        assertEquals(4.5,
                litre.subtract(ml).getValue(),
                EPSILON);
    }

    @Test
    void testVolumeDivision() {
        var litre = new Quantity<>(5.0, VolumeUnit.LITRE);
        var litre2 = new Quantity<>(10.0, VolumeUnit.LITRE);

        assertEquals(0.5, litre.divide(litre2), EPSILON);
    }

    /* =====================================================
       CROSS CATEGORY SAFETY
       ===================================================== */

    @Test
    void testCrossCategoryComparison() {
        var length = new Quantity<>(1.0, LengthUnit.FEET);
        var weight = new Quantity<>(1.0, WeightUnit.KILOGRAM);

        assertNotEquals(length, weight);
    }


    /* =====================================================
       DIVISION BY ZERO
       ===================================================== */

    @Test
    void testDivision_ByZero() {
        var length = new Quantity<>(10.0, LengthUnit.FEET);
        var zero = new Quantity<>(0.0, LengthUnit.FEET);

        assertThrows(ArithmeticException.class,
                () -> length.divide(zero));
    }
}