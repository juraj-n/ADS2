package dataStructures.tree.quadTree;

import dataStructures.common.QuadKey;

public class QuadDouble implements QuadKey {
    public final double value;
    public QuadDouble(double value) {
        this.value = value;
    }
    @Override
    public QuadKey subtract(QuadKey other) {
        return new QuadDouble(this.value - ((QuadDouble)other).value);
    }
    @Override
    public QuadKey half() {
        return new QuadDouble(this.value / 2);
    }
    @Override
    public int compare(QuadKey other) {
        return Double.compare(this.value, ((QuadDouble)other).value);
    }
}
