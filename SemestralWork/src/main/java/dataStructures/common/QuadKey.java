package dataStructures.common;

public interface QuadKey extends Comparable {
    QuadKey subtract(QuadKey other);
    QuadKey half();
}
