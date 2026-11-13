package dataStructures.dynHash.file;

import java.util.BitSet;

public interface Record<T> {
    boolean isEqualTo(T other);
    int getSize();
    BitSet getHash();
    byte[] toBytes();
    void fromBytes(byte[] bytes);
    T createClass();
}
