package dataStructures.tree.quadTree;

import dataStructures.common.QuadKey;

import java.util.ArrayList;
import java.util.LinkedList;

public class QuadNode<T> {
    private final QuadKey _xMin, _xMax, _yMin, _yMax;
    private ArrayList<QuadNode<T>> _sons;
    private LinkedList<T> _data;

    public QuadNode(QuadKey xSubMin, QuadKey xSubMax, QuadKey ySubMin, QuadKey ySubMax) {
        this._xMin = xSubMin;
        this._xMax = xSubMax;
        this._yMin = ySubMin;
        this._yMax = ySubMax;

        this._sons = new ArrayList<>(4); // 0-NW, 1-NE, 2-SW, 3-SE

        this._data = new LinkedList<>();
    }

    public QuadNode<T> getSon(int index) {
        return this._sons.get(index);
    }
    public int getQuadrantIndex(QuadKey xKey, QuadKey yKey) {
        QuadKey xHalf = this._xMax.subtract(this._xMax.subtract(this._xMin).half());
        QuadKey yHalf = this._yMax.subtract(this._yMax.subtract(this._yMin).half());

        if (xKey.compare(xHalf) == -1) { // WEST
            return yKey.compare(yHalf) == -1 ? 2 : 0;
        } else { // EAST
            return yKey.compare(yHalf) == -1 ? 3 : 1;
        }
    }
    public void insertData(T data) {
        this._data.add(data);
    }

    public boolean isLeaf() {
        for (QuadNode<T> son : this._sons) {
            if (son != null) {
                return false;
            }
        }
        return true;
    }
    public boolean hasData() {
        return !this._data.isEmpty();
    }
}
