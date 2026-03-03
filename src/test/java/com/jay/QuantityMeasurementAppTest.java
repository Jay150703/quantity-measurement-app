package com.jay;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuantityTest {

    private static final double EPSILON = 0.0001;

    /* =====================================================
       LENGTH TESTS (UC1–UC8 preserved)
       ===================================================== */

    @Test
    void testLengthEquality_FeetToInches() {
        Quantity<LengthUnit> feet =
                new Quantity<>(1.0, LengthUnit.FEET);

        Quantity<LengthUnit> inches =
                new Quantity<>(12.0, LengthUnit.INCHES);

        assertEquals(feet, inches);
    }

    @Test
    void testLengthConversion_FeetToInches() {
        Quantity<LengthUnit> feet =
                new Quantity<>(1.0, LengthUnit.FEET);

        Quantity<LengthUnit> converted =
                feet.convertTo(LengthUnit.INCHES);

        assertEquals(12.0, converted.getValue(), EPSILON);
    }

    @Test
    void testLengthAddition_CrossUnit() {
        Quantity<LengthUnit> feet =
                new Quantity<>(1.0, LengthUnit.FEET);

        Quantity<LengthUnit> inches =
                new Quantity<>(12.0, LengthUnit.INCHES);

        Quantity<LengthUnit> result =
                feet.add(inches, LengthUnit.FEET);

        assertEquals(2.0, result.getValue(), EPSILON);
    }

    /* =====================================================
       WEIGHT TESTS (UC9 preserved)
       ===================================================== */

    @Test
    void testWeightEquality_KgToGram() {
        Quantity<WeightUnit> kg =
                new Quantity<>(1.0, WeightUnit.KILOGRAM);

        Quantity<WeightUnit> gram =
                new Quantity<>(1000.0, WeightUnit.GRAM);

        assertEquals(kg, gram);
    }

    @Test
    void testWeightConversion_KgToPound() {
        Quantity<WeightUnit> kg =
                new Quantity<>(1.0, WeightUnit.KILOGRAM);

        Quantity<WeightUnit> pound =
                kg.convertTo(WeightUnit.POUND);

        assertEquals(2.20462, pound.getValue(), 0.01);
    }

    @Test
    void testWeightAddition_CrossUnit() {
        Quantity<WeightUnit> kg =
                new Quantity<>(1.0, WeightUnit.KILOGRAM);

        Quantity<WeightUnit> gram =
                new Quantity<>(1000.0, WeightUnit.GRAM);

        Quantity<WeightUnit> result =
                kg.add(gram, WeightUnit.KILOGRAM);

        assertEquals(2.0, result.getValue(), EPSILON);
    }

    /* =====================================================
       CROSS CATEGORY SAFETY
       ===================================================== */

    @Test
    void testCrossCategoryComparison() {
        Quantity<LengthUnit> length =
                new Quantity<>(1.0, LengthUnit.FEET);

        Quantity<WeightUnit> weight =
                new Quantity<>(1.0, WeightUnit.KILOGRAM);

        assertNotEquals(length, weight);
    }

    /* =====================================================
       VALIDATION TESTS
       ===================================================== */

    @Test
    void testConstructor_NullUnit() {
        assertThrows(IllegalArgumentException.class,
                () -> new Quantity<>(1.0, null));
    }

    @Test
    void testConstructor_InvalidValue() {
        assertThrows(IllegalArgumentException.class,
                () -> new Quantity<>(Double.NaN, LengthUnit.FEET));
    }

    /* =====================================================
       ADDITION PROPERTIES
       ===================================================== */

    @Test
    void testAddition_Commutativity_Length() {
        Quantity<LengthUnit> a =
                new Quantity<>(1.0, LengthUnit.FEET);

        Quantity<LengthUnit> b =
                new Quantity<>(12.0, LengthUnit.INCHES);

        assertEquals(
                a.add(b, LengthUnit.FEET),
                b.add(a, LengthUnit.FEET)
        );
    }

    @Test
    void testAddition_WithZero() {
        Quantity<WeightUnit> a =
                new Quantity<>(5.0, WeightUnit.KILOGRAM);

        Quantity<WeightUnit> zero =
                new Quantity<>(0.0, WeightUnit.GRAM);

        assertEquals(
                a,
                a.add(zero)
        );
    }

    /* =====================================================
       HASHCODE CONSISTENCY
       ===================================================== */

    @Test
    void testHashCodeConsistency() {
        Quantity<LengthUnit> a =
                new Quantity<>(1.0, LengthUnit.FEET);

        Quantity<LengthUnit> b =
                new Quantity<>(12.0, LengthUnit.INCHES);

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }
    // =====================
// UC11 - Volume Tests
// =====================

    @Test
    void testEquality_LitreToMillilitre() {
        var litre = new Quantity<>(1.0, VolumeUnit.LITRE);
        var ml = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);

        assertEquals(litre, ml);
    }

    @Test
    void testEquality_LitreToGallon() {
        var litre = new Quantity<>(3.78541, VolumeUnit.LITRE);
        var gallon = new Quantity<>(1.0, VolumeUnit.GALLON);

        assertEquals(litre, gallon);
    }

    @Test
    void testConversion_LitreToMillilitre() {
        var litre = new Quantity<>(1.0, VolumeUnit.LITRE);
        var converted = litre.convertTo(VolumeUnit.MILLILITRE);

        assertEquals(new Quantity<>(1000.0, VolumeUnit.MILLILITRE), converted);
    }

    @Test
    void testConversion_GallonToLitre() {
        var gallon = new Quantity<>(1.0, VolumeUnit.GALLON);
        var litre = gallon.convertTo(VolumeUnit.LITRE);

        assertEquals(new Quantity<>(3.78541, VolumeUnit.LITRE), litre);
    }

    @Test
    void testAddition_LitrePlusMillilitre() {
        var litre = new Quantity<>(1.0, VolumeUnit.LITRE);
        var ml = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);

        var result = litre.add(ml);

        assertEquals(new Quantity<>(2.0, VolumeUnit.LITRE), result);
    }

    @Test
    void testAddition_ExplicitTargetUnit_Gallon() {
        var litre = new Quantity<>(3.78541, VolumeUnit.LITRE);
        var litre2 = new Quantity<>(3.78541, VolumeUnit.LITRE);

        var result = litre.add(litre2, VolumeUnit.GALLON);

        assertEquals(new Quantity<>(2.0, VolumeUnit.GALLON), result);
    }

    @Test
    void testVolumeVsLength_Incompatible() {
        var volume = new Quantity<>(1.0, VolumeUnit.LITRE);
        var length = new Quantity<>(1.0, LengthUnit.FEET);

        assertNotEquals(volume, length);
    }

    @Test
    void testVolumeVsWeight_Incompatible() {
        var volume = new Quantity<>(1.0, VolumeUnit.LITRE);
        var weight = new Quantity<>(1.0, WeightUnit.KILOGRAM);

        assertNotEquals(volume, weight);
    }

}