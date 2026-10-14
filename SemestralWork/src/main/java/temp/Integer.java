package temp;

import dataStructures.common.QuadKey;

public class Integer implements QuadKey {

    public final int value;

    public Integer(int val) {
        this.value = val;
    }

    @Override
    public QuadKey subtract(QuadKey other) {
        return new Integer(this.value - ((Integer)other).value);
    }

    @Override
    public QuadKey half() {
        return new Integer(this.value / 2);
    }

    @Override
    public int compare(QuadKey other) {
        if (this.value < ((Integer)other).value) {
            return -1;
        } else if (this.value == ((Integer)other).value) {
            return 0;
        } else if (this.value > ((Integer)other).value) {
            return 1;
        }

        return -10;
    }
}
