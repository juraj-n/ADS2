package dataStructures.dynHash.indexTrie;

import dataStructures.dynHash.file.Record;

public class InternalNode<T extends Record<T>> extends IndexNode<T>{
    private IndexNode<T> _leftSon;
    private IndexNode<T> _rightSon;

    public IndexNode<T> getLeftSon() {
        return this._leftSon;
    }
    public void setLeftSon(IndexNode<T> son) {
        this._leftSon = son;
    }
    public IndexNode<T> getRightSon() {
        return this._rightSon;
    }
    public void setRightSon(IndexNode<T> son) {
        this._rightSon = son;
    }
}
