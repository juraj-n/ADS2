package dataStructures.tree.quadTree;

import dataStructures.common.QuadKey;

import java.util.LinkedList;

/**
 * Generic class represents QuadTree.
 *
 * @param <T> the type of value that is stored.
 * @author Juraj Nižnanský
 * */
public class QuadTree<T> {
    private final QuadKey _xMin, _xMax, _yMin, _yMax;
    private final int MAX_DEPTH;
    private final QuadNode<T> _root;

    public QuadTree(QuadKey xMin, QuadKey xMax, QuadKey yMin, QuadKey yMax, int maxDepth) {
        this._xMin = xMin;
        this._xMax = xMax;
        this._yMin = yMin;
        this._yMax = yMax;

        this.MAX_DEPTH = maxDepth;

        this._root = new QuadNode<>(xMin, xMax, yMin, yMax);
    }

    /**
     * Inserts keys and value into tree to the correct place.
     *
     * @param xKey the key that determines the position along x-axis.
     * @param yKey the key that determines the position along y-axis.
     * @param data the data that represents stored value.
     * @return true if the operation was successfull.
     * */
    public boolean insert(QuadKey xKey, QuadKey yKey, T data) {
        if (xKey.compare(this._xMin) == -1
                || xKey.compare(this._xMax) == 1
                || yKey.compare(this._yMin) == -1
                || yKey.compare(this._yMax) == 1
        ) {
            return false;
        }

        QuadNode<T> current = this._root;
        int depth = 0;
        while (true) {
            if (current.isLeaf() && !current.hasData()) {
                current.insertData(xKey, yKey, data);
                return true;
            }
            if (current.isLeaf() && current.hasData()) {
                if (depth == this.MAX_DEPTH - 1
                        || current.equalKeys(xKey, yKey)
                ) {
                    current.insertData(xKey, yKey, data);
                    return true;
                } else {
                    current.subdivide();
                }
            }
            // 0-NW, 1-NE, 2-SW, 3-SE
            int childIndex = current.getQuadrantIndex(xKey, yKey);

            current = current.getSon(childIndex);
            depth++;
        }
    }
    /**
     * Point search by xKey and yKey.
     *
     * @param xKey the key that determines the position of finding values along x-axis.
     * @param yKey the key that determines the position of finding values along y-axis.
     * @return LinkedList<T> of found values.
     * */
    public LinkedList<T> find(QuadKey xKey, QuadKey yKey) {
        return this.find(xKey, xKey, yKey, yKey);
    }
    /**
     * Interval search by rectangular region.
     *
     * @param xMinKey minimal value of xKey.
     * @param xMaxKey maximal value of xKey.
     * @param yMinKey minimal value of yKey.
     * @param yMaxKey maximal value of yKey.
     * @return LinkedList<T> of found values.
     * */
    public LinkedList<T> find(QuadKey xMinKey, QuadKey xMaxKey, QuadKey yMinKey, QuadKey yMaxKey) {
        // Exchange if min > max
        if (xMinKey.compare(xMaxKey) > 0) {
            QuadKey temp = xMaxKey;
            xMaxKey = xMinKey;
            xMinKey = temp;
        }
        if (yMinKey.compare(yMaxKey) > 0) {
            QuadKey temp = yMaxKey;
            yMaxKey = yMinKey;
            yMinKey = temp;
        }

        LinkedList<T> found = new LinkedList<>();

        LinkedList<QuadNode<T>> nodeStack = new LinkedList<>();
        nodeStack.push(this._root);
        while (!nodeStack.isEmpty()) {
            QuadNode<T> current = nodeStack.pop();

            if (current.hasData()) {
                // Check for data to be added to found
                for (var data : current.getData()) {
                    if (data.xKey().compare(xMinKey) >= 0
                            && data.xKey().compare(xMaxKey) <= 0
                            && data.yKey().compare(yMinKey) >= 0
                            && data.yKey().compare(yMaxKey) <= 0
                    ) {
                        found.add(data.getData());
                    }
                }
            }

            if (current.isLeaf())
                continue;

            int[] indices = {
                    current.getQuadrantIndex(xMinKey, yMinKey),
                    current.getQuadrantIndex(xMaxKey, yMinKey),
                    current.getQuadrantIndex(xMinKey, yMaxKey),
                    current.getQuadrantIndex(xMaxKey, yMaxKey)
            };
            boolean[] sonsPushed = new boolean[4];
            for (int index : indices) {
                if (!sonsPushed[index]) {
                    sonsPushed[index] = true;
                    nodeStack.push(current.getSon(index));
                }
            }
        }

        return found;
    }
}
