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
    private final int _blockFactor;
    private int _blockCount = 0;

    public DynHashFile(int blockFactor, String fileName) {
        this._blockFactor = blockFactor;
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
    public boolean insert(T record) {
        BitSet hash = record.getHash();

        IndexNode<T> current = this._root;
        int depth = 0;
        while (current instanceof InternalNode<T> internal) {
            current = hash.get(depth) ? internal.getRightSon() : internal.getLeftSon();
            depth++;
        }
        ExternalNode<T> external = (ExternalNode<T>) current;

        // No block allocated in node
        if (external.getBlockIndex() == -1) {
            external.setBlockIndex(this._blockCount++);

            Block<T> block = new Block<>(this._blockFactor, record.getClass());
            block.addRecord(record);
            this.writeBlock(external.getBlockIndex(), block);
            external.addCount();

            return true;
        }

        // Block is not full in node
        List<T> records = this.readBlock(external.getBlockIndex(), record).getRecords();
        // TODO: check for duplicate keys?
        if (external.getCount() < this._blockFactor) {
            records.add(record);
            // TODO: sort records?
            Block<T> block = new Block<>(this._blockFactor, record.getClass());
            block.setRecords(records);
            this.writeBlock(external.getBlockIndex(), block);
            external.addCount();

            return true;
        }

        // Block is full in node - divide
        records.add(record);
        int oldBlockIndex = external.getBlockIndex();

        while (true) {
            // Transform node into internal with two external sons
            InternalNode<T> internal = new InternalNode<>();
            internal.setParent(external.getParent());
            this.repairParent(external, internal);
            ExternalNode<T> left = new ExternalNode<>();
            ExternalNode<T> right = new ExternalNode<>();
            left.setParent(internal);
            right.setParent(internal);
            internal.setLeftSon(left);
            internal.setRightSon(right);

            // Redistribute
            List<T> leftRecords = new ArrayList<>();
            List<T> rightRecords = new ArrayList<>();
            for (T r : records) {
                if (r.getHash().get(depth)) {
                    rightRecords.add(r);
                } else {
                    leftRecords.add(r);
                }
            }
            depth++;

            if (leftRecords.isEmpty()) {
                external = right;
                continue;
            }
            if (rightRecords.isEmpty()) {
                external = left;
                continue;
            }

            left.setBlockIndex(oldBlockIndex);
            left.setCount(leftRecords.size());
            Block<T> blockLeft = new Block<>(this._blockFactor, record.getClass());
            blockLeft.setRecords(leftRecords);
            this.writeBlock(left.getBlockIndex(), blockLeft);

            right.setBlockIndex(this._blockCount++);
            right.setCount(rightRecords.size());
            Block<T> blockRight = new Block<>(this._blockFactor, record.getClass());
            blockRight.setRecords(leftRecords);
            this.writeBlock(left.getBlockIndex(), blockRight);

            return true;
        }
    }
    public T find() {
        return null;
    }

    private void writeBlock(int blockIndex, Block<T> block) {
        byte[] bytes = block.toBytes();

        try {
            this._file.seek(blockIndex);
            this._file.write(bytes);
        } catch(IOException ioex) {
            System.out.println(ioex.getMessage());
        }
    }
    private Block<T> readBlock(int blockIndex, T record) {
        Block<T> block = new Block<>(this._blockFactor, record.getClass());

        byte[] bytes = new byte[block.getSize()];
        try {
            this._file.seek(blockIndex);
            this._file.read(bytes);
        } catch (IOException ioex) {
            System.out.println(ioex.getMessage());
        }

        block.fromBytes(bytes);
        return block;
    }
    private void repairParent(IndexNode<T> oldNode, IndexNode<T> newNode) {
        InternalNode<T> parent = oldNode.getParent();
        if (parent == null) {
            this._root = newNode;
        } else if (parent.getLeftSon() == oldNode) {
            parent.setLeftSon(newNode);
        } else {
            parent.setRightSon(newNode);
        }
    }
}
