package temp;

import dataStructures.common.QuadKey;

public class Double implements QuadKey {
    public final double value;

    public Double(double val) {
        this.value = val;
    }

    @Override
    public QuadKey subtract(QuadKey other) {
        return new Double(this.value - ((Double)other).value);
    }

    @Override
    public QuadKey half() {
        return new Double(this.value / 2);
    }

    @Override
    public int compare(QuadKey other) {
        if (this.value < ((Double)other).value) {
            return -1;
        } else if (this.value == ((Double)other).value) {
            return 0;
        } else if (this.value > ((Double)other).value) {
            return 1;
        }

        return -10;
    }
}
