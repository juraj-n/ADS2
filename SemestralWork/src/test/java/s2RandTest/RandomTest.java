package s2RandTest;

import dataStructures.dynHash.file.DynHashFile;
import misc.TestRecord;
import org.junit.jupiter.api.BeforeEach;
import misc.TestConstants;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RandomTest {
    private final long seed = System.nanoTime();
    private final Random generator = new Random(this.seed);
    private final DynHashFile<TestRecord> dynHashFile = new DynHashFile<>(TestConstants.BLOCK_FACTOR, "file.bin", new TestRecord());

    @BeforeEach
    public void consolePrint() {
        System.out.println("************************");
        System.out.println("         [TEST]         ");
        System.out.println("************************");
        System.out.println("SEED:. . " + this.seed + "L");
        System.out.println("OPERATION COUNT: " + TestConstants.NUMBER_OF_OPERATIONS);
        System.out.println("************************");
    }
    @Test
    public void testInsertFind() {
        int insertCount = 0;
        int findCount = 0;

        List<TestRecord> inserted = new ArrayList<>();
        for (int i = 0; i < TestConstants.NUMBER_OF_OPERATIONS; i++) {
            if (generator.nextDouble(100) < 50) {
                insertCount++;
                this.insertRandomKeys(this.dynHashFile, inserted);
            } else {
                findCount++;
                this.findRandomKeys(this.dynHashFile, inserted);
            }
        }

        System.out.println("[INSERTED] " + insertCount);
        System.out.println("[  FIND  ] " + findCount);
    }
    @Test
    public void testContainsExpected() {
        List<TestRecord> inserted = new ArrayList<>(TestConstants.NUMBER_OF_OPERATIONS);
        for (int i = 0; i < TestConstants.NUMBER_OF_OPERATIONS; i++) {
            insertRandomKeys(this.dynHashFile, inserted);
        }

        List<TestRecord> found = new ArrayList<>(TestConstants.NUMBER_OF_OPERATIONS);
        for (int i = 0; i < TestConstants.NUMBER_OF_OPERATIONS; i++) {
            TestRecord expected = inserted.get(i);
            TestRecord foundRecord = this.dynHashFile.find(expected);
            found.add(foundRecord);

            assertTrue(foundRecord.isEqualTo(expected), "Found record isn't equal to expected!");
        }

        assertEquals(inserted.size(), found.size(), "Expected size does not match found size!");
    }

    private void insertRandomKeys(DynHashFile<TestRecord> file, List<TestRecord> inserted) {
        TestRecord tr = new TestRecord();
        tr.id = generator.nextInt();
        tr.name = "" + generator.nextInt();

        if (file.insert(tr))
            inserted.add(tr);
    }
    private void findRandomKeys(DynHashFile<TestRecord> file, List<TestRecord> inserted) {
        if (!inserted.isEmpty()) {
            TestRecord expected = inserted.get(generator.nextInt(inserted.size()));

            //this.dynHashFile.printFile();

            TestRecord found = file.find(expected);

            assertTrue(found.isEqualTo(expected), "Found does not match expected!");
        }
    }
}
