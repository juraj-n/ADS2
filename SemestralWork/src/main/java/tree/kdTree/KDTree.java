package tree.kdTree;

import common.Comparable;

import java.util.Arrays;

public class KDTree<T> {
    private int _k;
    private KDNode<T> _root;

    public KDTree(int k) {
        this._k = k;
        this._root = null;
    }

    public boolean insert(Comparable[] keys, T data) {
        if(keys.length != this._k) {
            throw new IllegalArgumentException("Number of keys must be the same as number of dimesions!");
        }
        if(this._root == null) {
            KDNodeItem<T> newItem = new KDNodeItem<>(Arrays.copyOf(keys, keys.length), data);
            _root = new KDNode<>(newItem);

            return true;
        }

        KDNode<T> parent = this._root;
        int depth = 0;
        int dimensions = this._k;
        while(true) {
            int keyIndex = depth % dimensions;

            if(keys[keyIndex].compare(parent.getItem().getKey(keyIndex)) <= 0) {
                KDNode<T> leftSon = parent.getLeftSon();
                if(leftSon == null) {
                    parent.setLeftSon(keys, data);

                    return true;
                } else {
                    parent = leftSon;
                    depth++;
                }
            } else {
                KDNode<T> rightSon = parent.getRightSon();
                if(rightSon == null) {
                    parent.setRightSon(keys, data);

                    return true;
                } else {
                    parent = rightSon;
                    depth++;
                }
            }
        }
    }
    public KDNode<T> find() {
        return null;
    }
}
