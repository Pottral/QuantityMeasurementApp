public class Extended {

    // ----------- Enum for Units (Base = FEET) -----------
    enum LengthUnit {
        FEET(1.0),
        INCH(1.0 / 12.0),        // 1 inch = 1/12 feet
        YARDS(3.0),              // 1 yard = 3 feet
        CENTIMETERS(0.393701 / 12.0); // 1 cm = 0.393701 inch → in feet

        private final double toFeetFactor;

        LengthUnit(double toFeetFactor) {
            this.toFeetFactor = toFeetFactor;
        }

        public double toFeet(double value) {
            return value * toFeetFactor;
        }
    }

    // ----------- Generic Quantity Class -----------
    static class QuantityLength {
        private final double value;
        private final LengthUnit unit;

        public QuantityLength(double value, LengthUnit unit) {
            if (unit == null) {
                throw new IllegalArgumentException("Unit cannot be null");
            }
            this.value = value;
            this.unit = unit;
        }

        private double toFeet() {
            return unit.toFeet(value);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            QuantityLength other = (QuantityLength) obj;

            return Double.compare(this.toFeet(), other.toFeet()) == 0;
        }
    }

    // ----------- Main Method -----------
    public static void main(String[] args) {

        // Yard to Feet
        var q1 = new QuantityLength(1.0, LengthUnit.YARDS);
        var q2 = new QuantityLength(3.0, LengthUnit.FEET);

        System.out.println("Input: Quantity(1.0, YARDS) and Quantity(3.0, FEET)");
        System.out.println("Output: Equal (" + q1.equals(q2) + ")");

        // Yard to Inches
        var q3 = new QuantityLength(1.0, LengthUnit.YARDS);
        var q4 = new QuantityLength(36.0, LengthUnit.INCH);

        System.out.println("Input: Quantity(1.0, YARDS) and Quantity(36.0, INCHES)");
        System.out.println("Output: Equal (" + q3.equals(q4) + ")");

        // CM to Inches
        var q5 = new QuantityLength(1.0, LengthUnit.CENTIMETERS);
        var q6 = new QuantityLength(0.393701, LengthUnit.INCH);

        System.out.println("Input: Quantity(1.0, CM) and Quantity(0.393701, INCH)");
        System.out.println("Output: Equal (" + q5.equals(q6) + ")");
    }
}