package dataStructures.dynHash.file;

import dataStructures.dynHash.indexTrie.ExternalNode;
import dataStructures.dynHash.indexTrie.IndexNode;
import dataStructures.dynHash.indexTrie.InternalNode;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.BitSet;
import java.util.List;

public class DynHashFile<T extends Record<T>> {
    private IndexNode<T> _root;
    private RandomAccessFile _file;
    private final T _prototype;
    private final int _blockFactor;
    private int _blockCount = 0;

    public DynHashFile(int blockFactor, String fileName, T prototype) {
        this._blockFactor = blockFactor;
        this._prototype = prototype;

        this._root = new ExternalNode<>();
        try {
            this._file = new RandomAccessFile(fileName, "rw");
        } catch (FileNotFoundException fnfex) {
            System.out.println(fnfex.getMessage());
        }
    }

    public void close() {
        try {
            this._file.close();
        } catch (IOException ioex) {
            System.out.println(ioex.getMessage());
        }
    }
    public void printFile() {
        for (int i = 0; i < this._blockCount; i++) {
            Block<T> block = this.readBlock(i);
            System.out.println("Block [" + i + "] (" + block.getValidCount() + "/" + this._blockFactor + ")");

            block.print();

            System.out.println("_________________");
        }
    }
    public boolean insert(T record) {
        BitSet hash = record.getHash();

        IndexNode<T> current = this._root;
        int depth = 0;
        // Get to external node
        while (current instanceof InternalNode<T> internal) {
            current = hash.get(depth) ? internal.getRightSon() : internal.getLeftSon();
            depth++;
        }
        ExternalNode<T> external = (ExternalNode<T>) current;

        // External does not point to block
        if (external.getBlockIndex() == -1) {
            this.writeBlock(this._blockCount, List.of(record));
            external.addCount();
            external.setBlockIndex(this._blockCount++);

            return true;
        }
        // Block is not full
        else if (external.getCount() < this._blockFactor) {
            Block<T> block = this.readBlock(external.getBlockIndex());
            block.addRecords(List.of(record));

            this.writeBlock(external.getBlockIndex(), block.getRecords());
            external.addCount();

            return true;
        }


        return false;
    }

    private void writeBlock(int index, List<T> records) {
        Block<T> block = new Block<>(this._blockFactor, this._prototype);
        block.addRecords(records);

        byte[] blockBytes = block.toBytes();
        try {
            this._file.seek((long)index * block.getSize());
            this._file.write(blockBytes);
        } catch (IOException ioex) {
            throw new IllegalStateException("Error writing block to file. ", ioex);
        }
    }
    private Block<T> readBlock(int index) {
        Block<T> block = new Block<>(this._blockFactor, this._prototype);

        byte[] blockBytes = new byte[block.getSize()];
        try {
            this._file.seek((long) index * block.getSize());
            this._file.readFully(blockBytes);
        } catch (IOException ioex) {
            throw new IllegalStateException("Error reading block from file. ", ioex);
        }
        block.fromBytes(blockBytes);

        return block;
    }
}
