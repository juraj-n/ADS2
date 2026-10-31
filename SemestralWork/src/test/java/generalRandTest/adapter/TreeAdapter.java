package generalRandTest.adapter;

import java.util.List;

public interface TreeAdapter<T> {
    boolean insert(double[] keys, T value);
    List<T> find(double[] minKeys, double[] maxKeys);
    void delete(double[] keys);

}
