package tree.kdTree;

import common.Comparable;
import java.util.Arrays;

public class KDNodeItem<T> {
    private Comparable[] _keys;
    private T _data;

    public KDNodeItem(Comparable[] keys, T data) {
        this._keys = Arrays.copyOf(keys, keys.length);
        this._data = data;
    }

    public Comparable getKey(int index) {
        return this._keys[index];
    }
    public Comparable[] getKeys() {
        return Arrays.copyOf(this._keys, this._keys.length);
    }
    public void setKeys(Comparable[] keys) {
        this._keys = Arrays.copyOf(keys, keys.length);
    }
    public T getData() {
        return this._data;
    }
    public void setData(T data) {
        this._data = data;
    }
}
