package generalRandTest;

import generalRandTest.adapter.myAdapter.QuadTreeAdapter;
import generalRandTest.adapter.TreeAdapter;
import generalRandTest.misc.TestConstants;
import generalRandTest.misc.TestItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RandomTest {
    private final long seed = System.nanoTime();
    private final Random generator = new Random(this.seed);

    // private final KDTreeAdapter<TestiItem> tree = new KDTreeAdapter<>();
     private final QuadTreeAdapter<TestItem> tree = new QuadTreeAdapter<>();
    // private final QuadKDTreeAdapter<TestItem> tree = new QuadKDTreeAdapter<>();

    // private final OschrenkKDAdapter<TestItem> tree = new OschrenkKDAdapter<>();
    // private final VarunpantQuadAdapter<TestItem> tree = new VarunpantQuadAdapter<>();

    @BeforeEach
    public void consolePrint() {
        System.out.println("************************");
        System.out.println("         [TEST]         ");
        System.out.println("************************");
        System.out.println("SEED:. . " + this.seed + "L");
        System.out.println("OPERATION COUNT: " + TestConstants.NUMBER_OF_OPERATIONS);
        System.out.println("WORLD SIZE:. . . . ." + TestConstants.WORLD_SIZE);
        System.out.println("************************");
    }
    @Test
    public void testInsertFind() {
        int insertCount = 0;
        int findCount = 0;

        List<TestItem> inserted = new ArrayList<>();
        for (int i = 0; i < TestConstants.NUMBER_OF_OPERATIONS; i++) {
            if (generator.nextDouble(100) < 50) {
                insertCount++;
                insertRandomKeys(tree, inserted);
            } else {
                findCount++;
                findRandomRange(tree, inserted);
            }
        }

        System.out.println(tree.getClass().getSimpleName());
        System.out.println("[INSERTED] " + insertCount);
        System.out.println("[  FIND  ] " + findCount);
    }
    @Test
    public void testTreeContainsInserted() {
        List<TestItem> inserted = new ArrayList<>(TestConstants.NUMBER_OF_OPERATIONS);
        for (int i = 0; i < TestConstants.NUMBER_OF_OPERATIONS; i++) {
            insertRandomKeys(tree, inserted);
        }

        double[] minKeys = new double[TestConstants.DIM];
        double[] maxKeys = new double[TestConstants.DIM];
        for (int i = 0; i < TestConstants.DIM; i++) {
            minKeys[i] = 0;
            maxKeys[i] = TestConstants.WORLD_SIZE;
        }
        var found = tree.find(minKeys, maxKeys);
        assertEquals(inserted.size(), found.size(), "[" + tree.getClass().getSimpleName() + "] expected size does not match found size!");
        assertTrue(found.containsAll(inserted), "[" + tree.getClass().getSimpleName() + "] found does not contain expected!");
    }

    private void insertRandomKeys(TreeAdapter<TestItem> tree, List<TestItem> inserted) {
        double[] keys = new double[TestConstants.DIM];
        for (int i = 0; i < TestConstants.DIM; i++) {
            keys[i] = generator.nextInt(TestConstants.WORLD_SIZE); // nextDouble for no duplicate keys
        }
        TestItem data = new TestItem(keys, generator.nextDouble(TestConstants.WORLD_SIZE));

        if (tree.insert(keys, data))
            inserted.add(data);
    }
    private void findRandomRange(TreeAdapter<TestItem> tree, List<TestItem> inserted) {
        double[] minKeys = new double[TestConstants.DIM];
        double[] maxKeys = new double[TestConstants.DIM];
        for (int i = 0; i < TestConstants.DIM; i++) {
            minKeys[i] = generator.nextDouble(TestConstants.WORLD_SIZE);
            maxKeys[i] = minKeys[i] + 10;
        }

        var found = tree.find(minKeys, maxKeys);
        var expected = inserted.stream()
                .filter(item -> {
                    for (int i = 0; i < TestConstants.DIM; i++) {
                        if (item.keys[i] < minKeys[i] ||
                                item.keys[i] > maxKeys[i])
                        {
                            return false;
                        }
                    }
                    return true;
                })
                .collect(Collectors.toCollection(LinkedList::new));
        // Asserts
        assertEquals(expected.size(), found.size(), "[" + tree.getClass().getSimpleName() + "] expected size does not match found size!");
        assertTrue(found.containsAll(expected), "[" + tree.getClass().getSimpleName() + "] found does not contain expected!");
    }
}
