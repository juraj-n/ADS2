package dataStructures.tree.quadTree;

import dataStructures.common.QuadKey;

public class QuadTree<T> {
    private final QuadKey _xMin, _xMax, _yMin, _yMax;
    private final QuadNode<T> _root;

    public QuadTree(QuadKey xMin, QuadKey xMax, QuadKey yMin, QuadKey yMax) {
        this._xMin = xMin;
        this._xMax = xMax;
        this._yMin = yMin;
        this._yMax = yMax;

        this._root = null;
    }

    public boolean insert(QuadKey xKey, QuadKey yKey, T data) {
        if (xKey.compare(this._xMin) == -1
                || xKey.compare(this._xMax) == 1
                || yKey.compare(this._yMin) == -1
                || yKey.compare(this._yMax) == 1
        ) {
            return false;
        }

        // TODO: Current is null -> first insert goes to root

        QuadNode<T> current = this._root;
        while (true) {
            if (current.isLeaf() && !current.hasData()) {
                current.insertData(data);
                return true;
            }
            if (current.isLeaf() && current.hasData()) {
                this.subdivide(current);
            }
            // 0-NW, 1-NE, 2-SW, 3-SE
            int childIndex = current.getQuadrantIndex(xKey, yKey);

            current = current.getSon(childIndex);
        }
    }

    private void subdivide(QuadNode<T> node) {

    }
}
