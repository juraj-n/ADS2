package dataStructures.dynHash.test;

import dataStructures.dynHash.file.DynHashFile;

public class Main {
    public static void main(String[] args) {
        TestRecord tr = new TestRecord();
        tr.id = 4;
        tr.name = "TEST";

        DynHashFile<TestRecord> dynHashFile = new DynHashFile<>(2, "file.bin", tr);
        dynHashFile.insert(tr);

        dynHashFile.printFile();

        dynHashFile.close();
    }
}
