package dataStructures.common;

public interface QuadKey {
    QuadKey subtract(QuadKey other);
    QuadKey half();
    int compare(QuadKey other);
}
