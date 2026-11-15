package s2RandTest;

import dataStructures.dynHash.file.DynHashFile;
import misc.TestRecord;
import org.junit.jupiter.api.BeforeEach;
import misc.TestConstants;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomTest {
    private final long seed = System.nanoTime();
    private final Random generator = new Random(this.seed);
    private final DynHashFile<TestRecord> dynHashFile = new DynHashFile<>(3, "file.bin", new TestRecord());

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
    }

    private void insertRandomKeys(DynHashFile<TestRecord> file, List<TestRecord> inserted) {
        int id = generator.nextInt();
        TestRecord tr = new TestRecord();
        tr.id = id;
        tr.name = "random name" + generator.nextInt();

        if (file.insert(tr))
            inserted.add(tr);
    }
    private void findRandomKeys(DynHashFile<TestRecord> file, List<TestRecord> inserted) {
        if (inserted.size() > 0) {
            TestRecord random = inserted.get(generator.nextInt(inserted.size()));
            file.find(random);

            // TODO: finish tests
        }
    }
}
