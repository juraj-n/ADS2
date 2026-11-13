package dataStructures.dynHash.test;

import dataStructures.dynHash.file.DynHashFile;

public class Main {
    public static void main(String[] args) {
        TestRecord tr = new TestRecord();

        DynHashFile<TestRecord> dynHashFile = new DynHashFile<>(2, "file.bin");
        dynHashFile.insert(tr);

        dynHashFile.close();
    }
}
