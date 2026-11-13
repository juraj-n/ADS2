package dataStructures.dynHash.indexTrie;

import dataStructures.dynHash.file.Record;

public class ExternalNode<T extends Record<T>> extends IndexNode<T> {
    private int _count = 0;
    private int _index = -1; // -1 - no block allocated yet

    public void addCount() {
        this._count++;
    }
    public int getCount() {
        return this._count;
    }
    public void setCount(int count) {
        this._count = count;
    }

    public int getBlockIndex() {
        return this._index;
    }
    public void setBlockIndex(int index) {
        this._index = index;
    }
}
