package dataStructures.dynHash.test;

import dataStructures.dynHash.file.DynHashFile;

public class Main {
    public static void main(String[] args) {
        TestRecord tr = new TestRecord();
        tr.id = 1;
        tr.name = "TEST";

        TestRecord tr2 = new TestRecord();
        tr2.id = 2;
        tr2.name = "TEST_2";

        TestRecord tr3 = new TestRecord();
        tr3.id = 3;
        tr3.name = "TEST_3";

        TestRecord tr4 = new TestRecord();
        tr4.id = 4;
        tr4.name = "TEST_4";

        TestRecord tr5 = new TestRecord();
        tr5.id = 5;
        tr5.name = "TEST_5";

        DynHashFile<TestRecord> dynHashFile = new DynHashFile<>(2, "file.bin", tr);

        dynHashFile.insert(tr);
        dynHashFile.printFile();

        dynHashFile.insert(tr2);
        dynHashFile.printFile();

        dynHashFile.insert(tr3);
        dynHashFile.printFile();

        dynHashFile.insert(tr4);
        dynHashFile.printFile();

        dynHashFile.insert(tr5);
        dynHashFile.printFile();

        System.out.println("FOUND: ");
        TestRecord found = dynHashFile.find(tr3);
        found.print();

        dynHashFile.close();
    }
}
