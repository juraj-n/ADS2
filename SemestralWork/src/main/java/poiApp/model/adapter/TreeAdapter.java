package poiApp.model.adapter;

import java.util.List;

public interface TreeAdapter<T> {
    boolean insert(double[] keys, T value);
    List<T> fint(double[] keys);
    List<T> find(double[] minKeys, double[] maxKeys);
}
