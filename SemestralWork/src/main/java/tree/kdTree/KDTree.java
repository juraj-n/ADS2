package tree.kdTree;

import common.Comparable;

import java.util.Arrays;
import java.util.LinkedList;

public class KDTree<T> {
    private final int _k;
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
            _root = new KDNode<>(keys, data);

            return true;
        }

        KDNode<T> parent = this._root;
        int depth = 0;
        while(true) {
            int keyIndex = depth % this._k;

            if(keys[keyIndex].compare(parent.getKey(keyIndex)) <= 0) {
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
    public KDNode<T> find(Comparable[] keys) {
        return find(keys, keys).getFirst();
    }
    public LinkedList<KDNode<T>> find(Comparable[] minKeys, Comparable[] maxKeys) {
        LinkedList<T> found = new LinkedList<>();

        KDNode<T> parent = this._root;
        int depth = 0;

        while(true) {
            int keyIndex = depth % this._k;
            // K > MAX => leftSubtree
            if(maxKeys[keyIndex].compare(parent.getKey(keyIndex)) == -1) {
                KDNode<T> leftSon = parent.getLeftSon();
                if(leftSon == null) {

                }
            }
            // K = <MIN, MAX> => ? found & left + right Subtrees
            if(minKeys[keyIndex].compare(parent.getKey(keyIndex)) <= 0
                    && maxKeys[keyIndex].compare(parent.getKey(keyIndex)) >= 0
            ) {
                KDNode<T> leftSon = null;
                KDNode<T> rightSon = null;
            }
            // K < MIN => rightSubtree
            if(minKeys[keyIndex].compare(parent.getKey(keyIndex)) == 1) {
                KDNode<T> rightSon = null;
            }
        }
        
        return null;
    }
}
