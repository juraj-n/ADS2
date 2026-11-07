package poiApp.model.adapter;

import java.util.List;

/**
 * Adapter providing a common interface for multidimension tree structures.
 *
 * @param <T> the type of value that is stored in the tree.
 * @author Juraj Nižnanský
 * */
public interface TreeAdapter<T> {
    /**
     * Inserts the value into the tree based on keys.
     *
     * @param keys the keys that determine the position of value.
     * @param value the value that is inserted.
     * @return true if the operation was successfull.
     * */
    boolean insert(double[] keys, T value);
    /**
     * Point search by keys.
     *
     * @param keys the keys that determines the position of finding values.
     * @return LinkedList<T> of found values.
     * */
    List<T> find(double[] keys);
    /**
     * Interval search by region.
     *
     * @param minKeys minimal values of the region.
     * @param maxKeys maximal values of the region.
     * @return LinkedList<T> of found values.
     * */
    List<T> find(double[] minKeys, double[] maxKeys);
}
