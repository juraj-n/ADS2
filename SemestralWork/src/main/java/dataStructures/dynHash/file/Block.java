package dataStructures.dynHash.file;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Block<T extends Record<T>> {
    private final int _blockFactor;
    private int _validCount = 0;
    private List<T> _records;
    private final Class<T> _classType;

    public Block(int blockFactor, Class<T> classType) {
        this._blockFactor = blockFactor;
        this._records = new ArrayList<>(this._blockFactor);
        this._classType = classType;
    }

    public List<T> getRecords() {
        return this._records;
    }
    public void setRecords(List<T> records) {
        this._records = records;
    }
    public boolean addRecord(T record) {
        if (this._records.size() < this._blockFactor) {
            this._records.add(record);
            this._validCount++;

            return true;
        }

        return false;
    }
    public byte[] toBytes() {
        return null;
    }
    public void fromBytes(byte[] bytes) {

    }
    public int getSize() {
        try {
            return this._classType.newInstance().getSize() * this._blockFactor + Integer.BYTES;
        } catch (InstantiationException | IllegalAccessException ex) {
            System.out.println(ex.getMessage());
        }
        return 0;
    }
}
