package tree.kdTree;

import common.Comparable;

public class KDNode<T> {
    private KDNodeItem<T> _item;
    private KDNode<T> _leftSon;
    private KDNode<T> _rightSon;

    public KDNode(KDNodeItem<T> item) {
        this._item = item;
        this._leftSon = null;
        this._rightSon = null;
    }

    public KDNodeItem<T> getItem() {
        return this._item;
    }
    public KDNode<T> getLeftSon() {
        return this._leftSon;
    }
    public void setLeftSon(Comparable[] keys, T data) {
        this._leftSon = new KDNode<>(new KDNodeItem<>(keys, data));
    }
    public KDNode<T> getRightSon() {
        return this._rightSon;
    }
    public void setRightSon(Comparable[] keys, T data) {
        this._rightSon = new KDNode<>(new KDNodeItem<>(keys, data));
    }
}
