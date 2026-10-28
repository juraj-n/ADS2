package generalRandTest.adapter;

import dataStructures.common.QuadKey;

import java.util.List;

public interface TreeAdapter<T> {
    boolean insert(double[] keys, T value);
    List<T> find(double[] minKeys, double[] maxKeys);
}
