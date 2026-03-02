package com.jay;

public class QuantityMeasurementApp {

    public static class QuantityLength {

        private static final double EPSILON = 0.0001;

        private final double value;
        private final LengthUnit unit;

        public QuantityLength(double value, LengthUnit unit) {
            if (!Double.isFinite(value)) {
                throw new IllegalArgumentException("Invalid numeric value");
            }
            if (unit == null) {
                throw new IllegalArgumentException("Unit cannot be null");
            }
            this.value = value;
            this.unit = unit;
        }

        public QuantityLength add(QuantityLength other) {

            if (other == null) {
                throw new IllegalArgumentException("Second operand cannot be null");
            }

            double thisInFeet = this.toFeet();
            double otherInFeet = other.toFeet();

            double sumInFeet = thisInFeet + otherInFeet;

            // Convert back to unit of first operand
            double resultValue = this.unit.fromFeet(sumInFeet);

            return new QuantityLength(resultValue, this.unit);
        }

        private double toFeet() {
            return unit.toFeet(value);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            QuantityLength other = (QuantityLength) obj;
            return Math.abs(this.toFeet() - other.toFeet()) < EPSILON;
        }

        @Override
        public int hashCode() {
            return Double.hashCode(Math.round(toFeet() / EPSILON));
        }
    }

    public enum LengthUnit {
        FEET {
            public double toFeet(double value) {
                return value;
            }

            public double fromFeet(double value) {
                return value;
            }
        },
        INCHES {
            public double toFeet(double value) {
                return value / 12.0;
            }

            public double fromFeet(double value) {
                return value * 12.0;
            }
        },
        YARDS {
            public double toFeet(double value) {
                return value * 3.0;
            }

            public double fromFeet(double value) {
                return value / 3.0;
            }
        },
        CENTIMETERS {
            public double toFeet(double value) {
                return value / 30.48;
            }

            public double fromFeet(double value) {
                return value * 30.48;
            }
        };

        public abstract double toFeet(double value);
        public abstract double fromFeet(double value);
    }
}