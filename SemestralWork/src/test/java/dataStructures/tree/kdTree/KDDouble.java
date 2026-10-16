package dataStructures.tree.kdTree;

import dataStructures.common.Comparable;

public class KDDouble implements Comparable {
    public final double value;
    KDDouble(double value) {
        this.value = value;
    }
    @Override
    public int compare(Comparable other) {
        return Double.compare(this.value, ((KDDouble) other).value);
    }
}
