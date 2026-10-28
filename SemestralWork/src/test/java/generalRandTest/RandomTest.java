package generalRandTest;

import generalRandTest.adapter.myAdapter.KDTreeAdapter;
import generalRandTest.adapter.myAdapter.QuadKDTreeAdapter;
import generalRandTest.adapter.myAdapter.QuadTreeAdapter;
import generalRandTest.adapter.TreeAdapter;
import generalRandTest.adapter.s1t2Adapter.OschrenkKDAdapter;
import generalRandTest.adapter.s1t2Adapter.VarunpantQuadAdapter;
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

    private List<TreeAdapter<TestItem>> treesToTest() {
        List<TreeAdapter<TestItem>> trees = new ArrayList<>(2);
        // My trees
        trees.add(new KDTreeAdapter<>());
        trees.add(new QuadTreeAdapter<>());
        trees.add(new QuadKDTreeAdapter<>());
        // Other trees
        trees.add(new OschrenkKDAdapter<>());
        trees.add(new VarunpantQuadAdapter<>());

        return trees;
    }
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
        for (var tree : this.treesToTest()) {
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
            System.out.println("________________________");
        }
    }
    @Test
    public void testTreeContainsInserted() {
        for (var tree : this.treesToTest()) {
            List<TestItem> inserted = new ArrayList<>(TestConstants.NUMBER_OF_OPERATIONS);
            for (int i = 0; i < TestConstants.NUMBER_OF_OPERATIONS; i++) {
                insertRandomKeys(tree, inserted);
            }

            double[] minKeys = new double[TestConstants.K_DIM];
            double[] maxKeys = new double[TestConstants.K_DIM];
            for (int i = 0; i < TestConstants.K_DIM; i++) {
                minKeys[i] = 0;
                maxKeys[i] = TestConstants.WORLD_SIZE;
            }
            var found = tree.find(minKeys, maxKeys);
            assertEquals(inserted.size(), found.size(), "[" + tree.getClass().getSimpleName() + "] expected size does not match found size!");
            assertTrue(found.containsAll(inserted), "[" + tree.getClass().getSimpleName() + "] found does not contain expected!");
        }
    }

    private void insertRandomKeys(TreeAdapter<TestItem> tree, List<TestItem> inserted) {
        double[] keys = new double[TestConstants.K_DIM];
        for (int i = 0; i < TestConstants.K_DIM; i++) {
            keys[i] = generator.nextInt(TestConstants.WORLD_SIZE);
        }
        TestItem data = new TestItem(keys, generator.nextDouble(TestConstants.WORLD_SIZE));

        if (tree.insert(keys, data))
            inserted.add(data);
    }
    private void findRandomRange(TreeAdapter<TestItem> tree, List<TestItem> inserted) {
        double[] minKeys = new double[TestConstants.K_DIM];
        double[] maxKeys = new double[TestConstants.K_DIM];
        for (int i = 0; i < TestConstants.K_DIM; i++) {
            minKeys[i] = generator.nextDouble(TestConstants.WORLD_SIZE);
            maxKeys[i] = minKeys[i] + 10;
        }

        var found = tree.find(minKeys, maxKeys);
        var expected = inserted.stream()
                .filter(item -> {
                    for (int i = 0; i < TestConstants.K_DIM; i++) {
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
