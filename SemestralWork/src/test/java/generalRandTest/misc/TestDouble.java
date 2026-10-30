package generalRandTest.misc;

import dataStructures.common.Comparable;
import dataStructures.common.QuadKey;

public class TestDouble implements QuadKey {
    public final double value;
    public TestDouble(double value) {
        this.value = value;
    }
    @Override
    public QuadKey subtract(QuadKey other) {
        return new TestDouble(this.value - ((TestDouble)other).value);
    }
    @Override
    public QuadKey half() {
        return new TestDouble(this.value / 2);
    }
    @Override
    public int compare(Comparable other) {
        if (this.value < ((TestDouble)other).value)
            return -1;
        else if (this.value > ((TestDouble)other).value)
            return 1;
        else
            return 0;
    }
}
