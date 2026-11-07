package dataStructures.tree.kdTree;

import dataStructures.common.Comparable;

import java.util.LinkedList;

/**
 * Generic class represents KDTree.
 *
 * @param <T> the type of value that is stored.
 * @author Juraj Nižnanský
 * */
public class KDTree<T> {
    private final int _k;
    private KDNode<T> _root;

    public KDTree(int k) {
        if (k < 2)
            throw new IllegalArgumentException("K must be at least 2!");
        
        this._k = k;
        this._root = null;
    }

    /**
     * Inserts keys and value into tree to the correct place.
     *
     * @param keys the keys that determine the position.
     * @param data the data that represents stored value.
     * @throws IllegalArgumentException when the number of keys is not equal to K dimensions.
     * @return true if the operation was successfull.
     * */
    public boolean insert(Comparable[] keys, T data) {
        if (keys.length != this._k) {
            throw new IllegalArgumentException("Number of keys must be the same as number of dimesions!");
        }
        if (this._root == null) {
            _root = new KDNode<>(keys, data);

            return true;
        }

        KDNode<T> parent = this._root;
        int depth = 0;
        while (true) {
            int keyIndex = depth % this._k;

            if (keys[keyIndex].compare(parent.getKey(keyIndex)) <= 0) {
                KDNode<T> leftSon = parent.getLeftSon();
                if (leftSon == null) {
                    parent.setLeftSon(keys, data);

                    return true;
                } else {
                    parent = leftSon;
                    depth++;
                }
            } else {
                KDNode<T> rightSon = parent.getRightSon();
                if (rightSon == null) {
                    parent.setRightSon(keys, data);

                    return true;
                } else {
                    parent = rightSon;
                    depth++;
                }
            }
        }
    }
    /**
     * Point search by keys.
     *
     * @param keys the keys that determines the position of finding values.
     * @return LinkedList<T> of found values.
     * */
    public LinkedList<T> find(Comparable[] keys) {
        return this.find(keys, keys);
    }
    /**
     * Kód vytvorený pomocou AI, zdokumentované v kapitole 1.
     * Interval search by k-dimensional region.
     *
     * @param minKeys minimal values of the region.
     * @param maxKeys maximal values of the region.
     * @return LinkedList<T> of found values.
     * */
    public LinkedList<T> find(Comparable[] minKeys, Comparable[] maxKeys) {
        // Exchange keys if min > max
        for (int i = 0; i < this._k; i++) {
            if (minKeys[i].compare(maxKeys[i]) > 0) {
                Comparable temp = minKeys[i];
                minKeys[i] = maxKeys[i];
                maxKeys[i] = temp;
            }
        }

        LinkedList<T> found = new LinkedList<>();
        if (this._root == null) {
            return found;
        }

        // Stacks
        LinkedList<KDNode<T>> nodeStack = new LinkedList<>();
        LinkedList<Integer> depthStack = new LinkedList<>();

        nodeStack.push(this._root);
        depthStack.push(0);

        while (!nodeStack.isEmpty()) {
            KDNode<T> current = nodeStack.pop();
            int depth = depthStack.pop();
            int keyIndex = depth % this._k;

            Comparable currentKey = current.getKey(keyIndex);

            // Left Subtree MAX < Key - Reject right subtree
            if (maxKeys[keyIndex].compare(currentKey) == -1) {
                KDNode<T> leftSon = current.getLeftSon();
                if (leftSon != null) {
                    nodeStack.push(leftSon);
                    depthStack.push(depth + 1);
                }
            }
            // Right Subtree MIN > Key - Reject left subtree
            else if (minKeys[keyIndex].compare(currentKey) == 1) {
                KDNode<T> rightSon = current.getRightSon();
                if (rightSon != null) {
                    nodeStack.push(rightSon);
                    depthStack.push(depth + 1);
                }
            }
            // Both Subtrees Key: <MIN, MAX>
            else {
                if (this.nodeInInterval(current, minKeys, maxKeys)) {
                    found.add(current.getData());
                }

                KDNode<T> rightSon = current.getRightSon();
                if (rightSon != null) {
                    nodeStack.push(rightSon);
                    depthStack.push(depth + 1);
                }
                KDNode<T> leftSon = current.getLeftSon();
                if (leftSon != null) {
                    nodeStack.push(leftSon);
                    depthStack.push(depth + 1);
                }
            }
        }

        return found;
    }
    public KDNode<T> getRoot() {
        return this._root;
    }
    public boolean isEmpty() {
        return this._root == null;
    }
    /**
     * Checks if node is in the k-dimensional region.
     *
     * @param node the node to be checked.
     * @param minKeys minimal values of the region.
     * @param maxKeys maximal values of the region.
     * @return true if the node belongs to each interval.
     * */
    private boolean nodeInInterval(KDNode<T> node, Comparable[] minKeys, Comparable[] maxKeys) {
        for (int i = 0; i < this._k; i++) {
            if (minKeys[i].compare(node.getKey(i)) == 1
                    || maxKeys[i].compare(node.getKey(i)) == -1) {
                return false;
            }
        }

        return true;
    }
}
