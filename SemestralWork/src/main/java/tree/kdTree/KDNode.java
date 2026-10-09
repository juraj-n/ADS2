package tree.kdTree;

import common.Comparable;
import java.util.Arrays;

public class KDNode<T> {
    private Comparable[] _keys;
    private T _data;
    private KDNode<T> _leftSon;
    private KDNode<T> _rightSon;

    public KDNode(Comparable[] keys, T data) {
        this._keys = Arrays.copyOf(keys, keys.length);
        this._data = data;
        this._leftSon = null;
        this._rightSon = null;
    }

    public KDNode<T> getLeftSon() {
        return this._leftSon;
    }
    public void setLeftSon(Comparable[] keys, T data) {
        this._leftSon = new KDNode<>(keys, data);
    }
    public KDNode<T> getRightSon() {
        return this._rightSon;
    }
    public void setRightSon(Comparable[] keys, T data) {
        this._rightSon = new KDNode<>(keys, data);
    }
    public Comparable getKey(int index) {
        return this._keys[index];
    }
    public T getData() {
        return this._data;
    }
}
