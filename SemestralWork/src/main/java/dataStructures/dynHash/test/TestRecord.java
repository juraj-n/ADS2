package dataStructures.dynHash.test;

import dataStructures.dynHash.file.Record;

import java.io.*;
import java.util.BitSet;

public class TestRecord implements Record<TestRecord> {
    public static final int NAME_LEN = 15;
    public int id;
    public String name;
    public TestRecord() {
        this.id = -1;
        this.name = "";
    }

    @Override
    public boolean isEqualTo(TestRecord other) {
        return this.id == other.id;
    }
    @Override
    public int getSize() {
        return Integer.BYTES + NAME_LEN * Character.BYTES;
    }
    @Override
    public BitSet getHash() {
        return BitSet.valueOf(new long[] {this.id});
    }
    @Override
    public byte[] toBytes() {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        DataOutputStream outStream = new DataOutputStream(byteStream);

        try {
            outStream.writeInt(this.id);
            String padding = String.format("%-" + NAME_LEN + "s", this.name);
            outStream.writeChars(padding.substring(0, NAME_LEN));
        } catch (IOException ioex) {
            throw new IllegalStateException("Error creating byte array from Test Record. ", ioex);
        }

        return byteStream.toByteArray();
    }
    @Override
    public void fromBytes(byte[] bytes) {
        ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
        DataInputStream inStream = new DataInputStream(byteStream);

        try {
            this.id = inStream.readInt();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < NAME_LEN; i++) {
                sb.append(inStream.readChar());
            }
            this.name = sb.toString().stripTrailing();
        } catch (IOException ioex) {
            throw new IllegalStateException("Error reading Test Record from bytes. ", ioex);
        }
    }
    @Override
    public TestRecord createClass() {
        return new TestRecord();
    }
    @Override
    public void print() {
        System.out.println("ID: " + this.id + ", Name: " + this.name);
    }
}
