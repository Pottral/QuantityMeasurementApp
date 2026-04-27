public class WeightUnits {

    // ----------- Length Unit (Standalone Enum) -----------
    enum LengthUnit {
        FEET(1.0),
        INCHES(1.0 / 12.0),
        YARDS(3.0),
        CENTIMETERS(1.0 / 30.48);

        private final double toFeetFactor;

        LengthUnit(double toFeetFactor) {
            this.toFeetFactor = toFeetFactor;
        }

        public double convertToBaseUnit(double value) {
            return value * toFeetFactor;
        }

        public double convertFromBaseUnit(double baseValue) {
            return baseValue / toFeetFactor;
        }
    }

    // ----------- QuantityLength Class -----------
    static class QuantityLength {
        private final double value;
        private final LengthUnit unit;

        public QuantityLength(double value, LengthUnit unit) {
            if (unit == null) throw new IllegalArgumentException("Unit cannot be null");
            if (!Double.isFinite(value)) throw new IllegalArgumentException("Invalid value");

            this.value = value;
            this.unit = unit;
        }

        public QuantityLength convertTo(LengthUnit target) {
            double base = unit.convertToBaseUnit(value);
            return new QuantityLength(target.convertFromBaseUnit(base), target);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            QuantityLength other = (QuantityLength) obj;

            double a = unit.convertToBaseUnit(value);
            double b = other.unit.convertToBaseUnit(other.value);

            return Math.abs(a - b) < 1e-6;
        }

        public QuantityLength add(QuantityLength other) {
            double sum = unit.convertToBaseUnit(value)
                    + other.unit.convertToBaseUnit(other.value);

            return new QuantityLength(unit.convertFromBaseUnit(sum), unit);
        }

        public static QuantityLength add(QuantityLength q1, QuantityLength q2, LengthUnit target) {
            double sum = q1.unit.convertToBaseUnit(q1.value)
                    + q2.unit.convertToBaseUnit(q2.value);

            return new QuantityLength(target.convertFromBaseUnit(sum), target);
        }

        @Override
        public String toString() {
            return "Quantity(" + value + ", " + unit + ")";
        }
    }

    // ----------- Weight Unit (Standalone Enum) -----------
    enum WeightUnit {
        KILOGRAM(1.0),
        GRAM(0.001),
        POUND(0.453592);

        private final double toKgFactor;

        WeightUnit(double toKgFactor) {
            this.toKgFactor = toKgFactor;
        }

        public double convertToBaseUnit(double value) {
            return value * toKgFactor;
        }

        public double convertFromBaseUnit(double baseValue) {
            return baseValue / toKgFactor;
        }
    }

    // ----------- QuantityWeight Class -----------
    static class QuantityWeight {
        private final double value;
        private final WeightUnit unit;

        public QuantityWeight(double value, WeightUnit unit) {
            if (unit == null) throw new IllegalArgumentException("Unit cannot be null");
            if (!Double.isFinite(value)) throw new IllegalArgumentException("Invalid value");

            this.value = value;
            this.unit = unit;
        }

        public QuantityWeight convertTo(WeightUnit target) {
            double base = unit.convertToBaseUnit(value);
            return new QuantityWeight(target.convertFromBaseUnit(base), target);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            QuantityWeight other = (QuantityWeight) obj;

            double a = unit.convertToBaseUnit(value);
            double b = other.unit.convertToBaseUnit(other.value);

            return Math.abs(a - b) < 1e-6;
        }

        public QuantityWeight add(QuantityWeight other) {
            double sum = unit.convertToBaseUnit(value)
                    + other.unit.convertToBaseUnit(other.value);

            return new QuantityWeight(unit.convertFromBaseUnit(sum), unit);
        }

        public static QuantityWeight add(QuantityWeight q1, QuantityWeight q2, WeightUnit target) {
            double sum = q1.unit.convertToBaseUnit(q1.value)
                    + q2.unit.convertToBaseUnit(q2.value);

            return new QuantityWeight(target.convertFromBaseUnit(sum), target);
        }

        @Override
        public String toString() {
            return "Quantity(" + value + ", " + unit + ")";
        }
    }

    // ----------- MAIN METHOD -----------
    public static void main(String[] args) {

        // -------- LENGTH --------
        QuantityLength l1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength l2 = new QuantityLength(12.0, LengthUnit.INCHES);

        System.out.println("Length Equality: " + l1.equals(l2));
        System.out.println("Length Add: " + l1.add(l2));
        System.out.println("Length Convert: " + l1.convertTo(LengthUnit.INCHES));

        // -------- WEIGHT --------
        QuantityWeight w1 = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
        QuantityWeight w2 = new QuantityWeight(1000.0, WeightUnit.GRAM);

        System.out.println("Weight Equality: " + w1.equals(w2));
        System.out.println("Weight Add: " + w1.add(w2));
        System.out.println("Weight Convert: " + w1.convertTo(WeightUnit.POUND));

        // -------- CROSS CHECK --------
        System.out.println("Weight vs Length: " + w1.equals(l1));
    }
}