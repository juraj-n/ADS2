package s1t2.loadData;

import dataStructures.common.Comparable;
import dataStructures.common.QuadKey;

public class SvkDouble implements QuadKey {
    private final double value;
    public SvkDouble(double val) {
        this.value = val;
    }

    @Override
    public QuadKey subtract(QuadKey other) {
        return new SvkDouble(this.value - ((SvkDouble)other).value);
    }
    @Override
    public QuadKey half() {
        return new SvkDouble(this.value / 2);
    }
    @Override
    public int compare(Comparable other) {
        return Double.compare(this.value, ((SvkDouble)other).value);
    }
}
