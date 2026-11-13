package dataStructures.dynHash.test;

import dataStructures.dynHash.file.Record;

import java.util.BitSet;

public class TestRecord implements Record<TestRecord> {
    private final int id = 4;
    @Override
    public boolean isEqualTo(TestRecord other) {
        return false;
    }
    @Override
    public int getSize() {
        return 0;
    }
    @Override
    public BitSet getHash() {
        return BitSet.valueOf(new long[] {this.id});
    }
    @Override
    public byte[] toBytes() {
        return new byte[0];
    }
    @Override
    public void fromBytes(byte[] bytes) {

    }
    @Override
    public TestRecord createClass() {
        return null;
    }
}
