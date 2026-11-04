package poiApp.model;

import dataStructures.common.Comparable;
import dataStructures.common.QuadKey;

public class PoiCoordinate implements QuadKey {
    private final double value;
    public PoiCoordinate(double value) {
        this.value = value;
    }

    @Override
    public int compare(Comparable other) {
        if (this.value < ((PoiCoordinate)other).getValue())
            return -1;
        else if (this.value == ((PoiCoordinate)other).getValue())
            return 0;
        else
            return 1;
    }
    @Override
    public QuadKey subtract(QuadKey other) {
        return new PoiCoordinate(this.value - ((PoiCoordinate)other).getValue());
    }
    @Override
    public QuadKey half() {
        return new PoiCoordinate(this.value / 2);
    }
    public double getValue() {
        return this.value;
    }
}
