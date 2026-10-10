package dataStructures.tree.kdTree;

import dataStructures.common.Comparable;

import java.util.LinkedList;

public class KDTree<T> {
    private final int _k;
    private KDNode<T> _root;

    public KDTree(int k) {
        this._k = k;
        this._root = null;
    }

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
    public LinkedList<KDNode<T>> find(Comparable[] keys) {
        return find(keys, keys);
    }
    /**
     * Kód vytvorený pomocou AI, zdokumentované v kapitole Y.
     * */
    // TODO: doplniť kapitolu
    public LinkedList<KDNode<T>> find(Comparable[] minKeys, Comparable[] maxKeys) {
        LinkedList<KDNode<T>> found = new LinkedList<>();
        if(this._root == null) {
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

            // Left Subtree MAX < Key
            if (maxKeys[keyIndex].compare(current.getKey(keyIndex)) == -1) {
                KDNode<T> leftSon = current.getLeftSon();
                if (leftSon != null) {
                    nodeStack.push(leftSon);
                    depthStack.push(depth + 1);
                }
            }
            // Right Subtree MIN > Key
            else if (minKeys[keyIndex].compare(current.getKey(keyIndex)) == 1) {
                KDNode<T> rightSon = current.getRightSon();
                if(rightSon != null) {
                    nodeStack.push(rightSon);
                    depthStack.push(depth + 1);
                }
            }
            // Both Subtrees Key: <MIN, MAX>
            else {
                if (this.nodeInInterval(current, minKeys, maxKeys)) {
                    found.add(current);
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
