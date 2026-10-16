package dataStructures.tree.quadTree;

import dataStructures.common.QuadKey;

public class QuadItem<T> {
    private final QuadKey _xKey, _yKey;
    private final T _data;

    public QuadItem(QuadKey xKey, QuadKey yKey, T data) {
        this._xKey = xKey;
        this._yKey = yKey;
        this._data = data;
    }

    public QuadKey xKey() {
        return this._xKey;
    }
    public QuadKey yKey() {
        return this._yKey;
    }
    public T getData() {
        return this._data;
    }
}
