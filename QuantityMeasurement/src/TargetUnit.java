public class TargetUnit {

    // ----------- Enum (Base = FEET) -----------
    enum LengthUnit {
        FEET(1.0),
        INCHES(1.0 / 12.0),
        YARDS(3.0),
        CENTIMETERS(0.393701 / 12.0);

        private final double toFeetFactor;

        LengthUnit(double toFeetFactor) {
            this.toFeetFactor = toFeetFactor;
        }

        public double toFeet(double value) {
            return value * toFeetFactor;
        }

        public double fromFeet(double feetValue) {
            return feetValue / toFeetFactor;
        }
    }

    // ----------- Quantity Class -----------
    static class QuantityLength {
        private final double value;
        private final LengthUnit unit;

        public QuantityLength(double value, LengthUnit unit) {
            if (unit == null) throw new IllegalArgumentException("Unit cannot be null");
            if (!Double.isFinite(value)) throw new IllegalArgumentException("Invalid value");

            this.value = value;
            this.unit = unit;
        }

        // Convert to base
        private double toFeet() {
            return unit.toFeet(value);
        }

        // ----------- PRIVATE HELPER (DRY) -----------
        private static double sumInFeet(QuantityLength q1, QuantityLength q2) {
            return q1.toFeet() + q2.toFeet();
        }

        // ----------- UC6 (default: first operand unit) -----------
        public QuantityLength add(QuantityLength other) {
            if (other == null) throw new IllegalArgumentException("Other null");

            double sumFeet = sumInFeet(this, other);
            double result = this.unit.fromFeet(sumFeet);

            return new QuantityLength(result, this.unit);
        }

        // ----------- UC7 (EXPLICIT TARGET UNIT) -----------
        public static QuantityLength add(QuantityLength q1,
                                         QuantityLength q2,
                                         LengthUnit targetUnit) {

            if (q1 == null || q2 == null)
                throw new IllegalArgumentException("Operands cannot be null");

            if (targetUnit == null)
                throw new IllegalArgumentException("Target unit cannot be null");

            double sumFeet = sumInFeet(q1, q2);
            double result = targetUnit.fromFeet(sumFeet);

            return new QuantityLength(result, targetUnit);
        }

        @Override
        public String toString() {
            return "Quantity(" + value + ", " + unit + ")";
        }
    }

    // ----------- Main Demo -----------
    public static void main(String[] args) {

        var a = new QuantityLength(1.0, LengthUnit.FEET);
        var b = new QuantityLength(12.0, LengthUnit.INCHES);

        System.out.println("Feet target → " +
                QuantityLength.add(a, b, LengthUnit.FEET));

        System.out.println("Inches target → " +
                QuantityLength.add(a, b, LengthUnit.INCHES));

        System.out.println("Yards target → " +
                QuantityLength.add(a, b, LengthUnit.YARDS));

        var c = new QuantityLength(2.54, LengthUnit.CENTIMETERS);
        var d = new QuantityLength(1.0, LengthUnit.INCHES);

        System.out.println("CM target → " +
                QuantityLength.add(c, d, LengthUnit.CENTIMETERS));
    }
}