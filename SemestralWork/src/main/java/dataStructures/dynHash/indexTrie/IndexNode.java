package dataStructures.dynHash.indexTrie;

import dataStructures.dynHash.file.Record;

public class IndexNode<T extends Record<T>> {
    private InternalNode<T> _parent;

    public InternalNode<T> getParent() {
        return this._parent;
    }
    public void setParent(InternalNode<T> parent) {
        this._parent = parent;
    }
}
