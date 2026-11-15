package dataStructures.dynHash.file;

import dataStructures.dynHash.indexTrie.ExternalNode;
import dataStructures.dynHash.indexTrie.IndexNode;
import dataStructures.dynHash.indexTrie.InternalNode;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
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

            System.out.println("________________");
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
        if (external.getCount() < this._blockFactor) {
            Block<T> block = this.readBlock(external.getBlockIndex());
            block.addRecords(List.of(record));

            this.writeBlock(external.getBlockIndex(), block.getRecords());
            external.addCount();

            return true;
        }
        // Block is full
        List<T> allRecords = new ArrayList<>(this.readBlock(external.getBlockIndex()).getRecords());
        allRecords.add(record);

        int oldBlockIndex = external.getBlockIndex();
        ExternalNode<T> currentToRedistribute = external;

        while (true) {
            InternalNode<T> newInternal = this.externalToInternal(currentToRedistribute);
            ExternalNode<T> leftSon = (ExternalNode<T>) newInternal.getLeftSon();
            ExternalNode<T> rightSon = (ExternalNode<T>) newInternal.getRightSon();

            List<T> leftRecords = new ArrayList<>();
            List<T> rightRecords = new ArrayList<>();
            for (T rec : allRecords) {
                if (rec.getHash().get(depth))
                    rightRecords.add(rec);
                else
                    leftRecords.add(rec);
            }

            depth++; // Adding depth for possible next iteration
            if (leftRecords.isEmpty()) { // Each record went right
                currentToRedistribute = rightSon;
                continue;
            }
            if (rightRecords.isEmpty()) { // Each record went left
                currentToRedistribute = leftSon;
                continue;
            }

            leftSon.setBlockIndex(oldBlockIndex);
            rightSon.setBlockIndex(this._blockCount++);

            leftSon.setCount(leftRecords.size());
            rightSon.setCount(rightRecords.size());

            this.writeBlock(leftSon.getBlockIndex(), leftRecords);
            this.writeBlock(rightSon.getBlockIndex(), rightRecords);

            return true;
        }
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

    private InternalNode<T> externalToInternal(ExternalNode<T> external) {
        InternalNode<T> internal = new InternalNode<>();
        ExternalNode<T> leftSon = new ExternalNode<>();
        ExternalNode<T> rightSon = new ExternalNode<>();

        internal.setLeftSon(leftSon);
        internal.setRightSon(rightSon);
        leftSon.setParent(internal);
        rightSon.setParent(internal);

        // Replace external with new internal node
        InternalNode<T> parent = external.getParent();
        if (parent == null) {
            this._root = internal;
        } else if (parent.getLeftSon() == external) {
            parent.setLeftSon(internal);
        } else {
            parent.setRightSon(internal);
        }

        return internal;
    }
}
