package dataStructures.tree.quadTree;

import dataStructures.common.QuadKey;

import java.util.ArrayList;
import java.util.LinkedList;

public class QuadNode<T> {
    private final QuadKey _xMin, _xMax, _yMin, _yMax;
    private ArrayList<QuadNode<T>> _sons;
    private LinkedList<QuadItem<T>> _data;

    public QuadNode(QuadKey xSubMin, QuadKey xSubMax, QuadKey ySubMin, QuadKey ySubMax) {
        this._xMin = xSubMin;
        this._xMax = xSubMax;
        this._yMin = ySubMin;
        this._yMax = ySubMax;

        this._sons = new ArrayList<>(4); // 0-NW, 1-NE, 2-SW, 3-SE
        this._data = new LinkedList<>();
    }

    public LinkedList<QuadItem<T>> getData() {
        return this._data;
    }
    public QuadNode<T> getSon(int index) {
        return this._sons.get(index);
    }
    public int getQuadrantIndex(QuadKey xKey, QuadKey yKey) {
        QuadKey xHalf = this._xMax.subtract(this._xMax.subtract(this._xMin).half());
        QuadKey yHalf = this._yMax.subtract(this._yMax.subtract(this._yMin).half());

        if (xKey.compare(xHalf) <= 0) { // WEST
            if (yKey.compare(yHalf) <= 0) { // SOUTH
                return 2;
            } else { // NORTH
                return 0;
            }
        } else { // EAST
            if (yKey.compare(yHalf) == -1) { // SOUTH
                return 3;
            } else { // NORTH
                return 1;
            }
        }
    }
    public void insertData(QuadKey xKey, QuadKey yKey, T data) {
        this._data.add(new QuadItem<>(xKey, yKey, data));
    }
    public void setData(LinkedList<QuadItem<T>> data) {
        this._data = data;
    }

    public boolean equalKeys(QuadKey xKey, QuadKey yKey) {
        for (var data : this._data) {
            if (data.xKey() == xKey && data.yKey() == yKey) {
                return true;
            }
        }
        return false;
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

    public void subdivide() {
        QuadKey xHalf = this._xMax.subtract(this._xMax.subtract(this._xMin).half());
        QuadKey yHalf = this._yMax.subtract(this._yMax.subtract(this._yMin).half());

        this._sons.add(0, new QuadNode<>(this._xMin, xHalf, yHalf, this._yMax)); // 0-NW
        this._sons.add(1, new QuadNode<>(xHalf, this._xMax, yHalf, this._yMax)); // 1-NE
        this._sons.add(2, new QuadNode<>(this._xMin, xHalf, this._yMin, yHalf)); // 2-SW
        this._sons.add(3, new QuadNode<>(xHalf, this._xMax, this._yMin, yHalf)); // 3-SE

        this.redistributeData();
    }
    private void redistributeData() {
        QuadItem<T> first = this._data.getFirst();
        int childIndex = this.getQuadrantIndex(first.xKey(), first.yKey());

        this._sons.get(childIndex).setData(this._data);
        this._data = new LinkedList<>();
    }
}
