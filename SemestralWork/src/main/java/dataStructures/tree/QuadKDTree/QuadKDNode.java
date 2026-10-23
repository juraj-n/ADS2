package dataStructures.tree.QuadKDTree;

import dataStructures.common.QuadKey;
import dataStructures.tree.kdTree.KDNode;
import dataStructures.tree.kdTree.KDTree;

import java.util.ArrayList;

public class QuadKDNode<T> {
    private final QuadKey _xMin, _xMax, _yMin, _yMax;
    private final ArrayList<QuadKDNode<T>> _sons;
    private KDTree<T> _data;

    public QuadKDNode(QuadKey xSubMin, QuadKey xSubMax, QuadKey ySubMin, QuadKey ySubMax) {
        this._xMin = xSubMin;
        this._xMax = xSubMax;
        this._yMin = ySubMin;
        this._yMax = ySubMax;

        this._sons = new ArrayList<>(4); // 0-NW, 1-NE, 2-SW, 3-SE
        this._data = new KDTree<>(2);
    }

    public KDTree<T> getData() {
        return this._data;
    }
    public QuadKDNode<T> getSon(int index) {
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
        this._data.insert(new QuadKey[] {xKey, yKey}, data);
    }
    public void setData(KDTree<T> data) {
        this._data = data;
    }

    public boolean equalKeys(QuadKey xKey, QuadKey yKey) {
        var found = this._data.find(new QuadKey[] {xKey, yKey});

        return found.isEmpty();
    }
    public boolean isLeaf() {
        for (QuadKDNode<T> son : this._sons) {
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

        this._sons.add(0, new QuadKDNode<>(this._xMin, xHalf, yHalf, this._yMax)); // 0-NW
        this._sons.add(1, new QuadKDNode<>(xHalf, this._xMax, yHalf, this._yMax)); // 1-NE
        this._sons.add(2, new QuadKDNode<>(this._xMin, xHalf, this._yMin, yHalf)); // 2-SW
        this._sons.add(3, new QuadKDNode<>(xHalf, this._xMax, this._yMin, yHalf)); // 3-SE

        this.redistributeData();
    }
    private void redistributeData() {
        KDNode<T> root = this._data.getRoot();
        int childIndex = this.getQuadrantIndex((QuadKey)root.getKey(0), (QuadKey)root.getKey(1));

        this._sons.get(childIndex).setData(this._data);
        this._data = new KDTree<>(2);
    }
}
