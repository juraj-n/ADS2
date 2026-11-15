package dataStructures.dynHash.file;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Block<T extends Record<T>> {
    private final int _blockFactor;
    private int _validCount = 0;
    private List<T> _records;
    private final T _prototype;

    public Block(int blockFactor, T prototype) {
        this._blockFactor = blockFactor;
        this._prototype = prototype;

        this._records = new ArrayList<>(this._blockFactor);
        for (int i = 0; i < this._blockFactor; i++) {
            this._records.add(this._prototype.createClass());
        }
    }

    public int getValidCount() {
        return this._validCount;
    }
    public List<T> getRecords() {
        return this._records.subList(0, this._validCount);
    }
    public void addRecords(List<T> records) {
        if ((records.size() + this._validCount) > this._blockFactor)
            throw new IllegalArgumentException("Too many records.");

        for (T rec : records) {
            this._records.set(this._validCount++, rec);
        }
    }
    public byte[] toBytes() {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        DataOutputStream outStream = new DataOutputStream(byteStream);

        try {
            outStream.writeInt(this._validCount);
            for (int i = 0; i < this._blockFactor; i++) {
                outStream.write(this._records.get(i).toBytes());
            }

            return byteStream.toByteArray();
        } catch (IOException ioex) {
            throw new IllegalStateException("Error creating byte array from block. ", ioex);
        }
    }
    public void fromBytes(byte[] bytes) {
        ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
        DataInputStream inStream = new DataInputStream(byteStream);

        try {
            this._validCount = inStream.readInt();

            byte[] recBytes = new byte[this._prototype.getSize()];
            for (int i = 0; i < this._blockFactor; i++) {
                inStream.readFully(recBytes);
                this._records.get(i).fromBytes(recBytes);
            }

        } catch (IOException ioex) {
            throw new IllegalStateException("Error converting byte array to block. ", ioex);
        }

    }
    public int getSize() {
        return Integer.BYTES + this._prototype.getSize() * this._blockFactor;
    }

    public void print() {
        for (int i = 0; i < this._records.size(); i++) {
            System.out.print("  [Record " + i + "] - ");
            if (i < this._validCount) {
                this._records.get(i).print();
            } else {
                System.out.println("(empty)");
            }
        }
    }
}
