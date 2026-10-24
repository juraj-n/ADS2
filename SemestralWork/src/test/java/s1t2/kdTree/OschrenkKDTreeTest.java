package s1t2.kdTree;

import dataStructures.tree.TestConstatns;
import dataStructures.tree.kdTree.KDItem;
import edu.wlu.cs.levy.cg.KDTree;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OschrenkKDTreeTest extends TestConstatns {
    private final long seed = System.nanoTime();
    private final Random generator = new Random(seed);
    @BeforeEach
    public void consolePrint() {
        System.out.println("************************");
        System.out.println(" [TEST]  KDTree  [TEST] ");
        System.out.println("************************");
        System.out.println("SEED:. . " + this.seed + "L");
        System.out.println("OPERATION COUNT: " + this.NUMBER_OF_OPERATIONS);
        System.out.println("DIMENSIONS:. . . . . ." + this.K_DIM);
        System.out.println("WORLD SIZE:. . . . ." + this.WORLD_SIZE);
        System.out.println("FIND [%]:. . . . . . " + this.FIND_PERC + "%");
        System.out.println("************************");
    }
    @Test
    public void testInsertFind() {
        int insertCount = 0;
        int findCount = 0;

        KDTree<OschrenkItem> tree = new KDTree<>(this.K_DIM);
        List<OschrenkItem> insItems = new ArrayList<>();
        for (int i = 0; i < this.NUMBER_OF_OPERATIONS; i++) {
            if (generator.nextDouble(100) < 50) {
                insertCount++;
                this.insertRandomKeys(tree, insItems);
            } else {
                findCount++;
                this.findRandomRange(tree, insItems);
            }
        }
        System.out.println("[INSERT] . . . . . " + insertCount);
        System.out.println("[ FIND ] . . . . . " + findCount);
    }
    @Test
    public void testContainsInserted() {
        KDTree<OschrenkItem> tree = new KDTree<>(this.K_DIM);
        List<OschrenkItem> insItems = new ArrayList<>(this.NUMBER_OF_OPERATIONS);
        for (int i = 0; i < this.NUMBER_OF_OPERATIONS; i++) {
            this.insertRandomKeys(tree, insItems);
        }

        double[] minKeys = {0, 0 };
        double[] maxKeys = {this.WORLD_SIZE, this.WORLD_SIZE};
        try {
            List<OschrenkItem> found = tree.range(minKeys, maxKeys);

            assertEquals(insItems.size(), found.size());
            assertTrue(found.containsAll(insItems));
        } catch (Exception ex) {
            System.out.println("[ERROR] " + ex.getMessage());
        }
    }

    private void insertRandomKeys(KDTree<OschrenkItem> tree, List<OschrenkItem> insItems) {
        double[] keys = new double[this.K_DIM];
        for (int keyIndex = 0; keyIndex < this.K_DIM; keyIndex++) {
            keys[keyIndex] = generator.nextDouble(this.WORLD_SIZE);
        }
        double data = generator.nextDouble();
        OschrenkItem item = new OschrenkItem(keys, data);

        try {
            tree.insert(keys, item);
            insItems.add(item);
        } catch (Exception ex) {
            System.out.println("[ERROR] " + ex.getMessage());
        }
    }
    private void findRandomRange(KDTree<OschrenkItem> tree, List<OschrenkItem> insItems) {
        double[] minKeys = new double[this.K_DIM];
        double[] maxKeys = new double[this.K_DIM];

        double side = Math.pow(this.FIND_PERC / 100.0, 1.0 / this.K_DIM) * this.WORLD_SIZE;
        for (int index = 0; index < this.K_DIM; index++) {
            double min = generator.nextDouble() * (this.WORLD_SIZE - side);
            double max = min + side;

            minKeys[index] = min;
            minKeys[index] = max;
        }

        try {
            List<OschrenkItem> found = tree.range(minKeys, maxKeys);

            if (this.FIND_PERC <= 5) {
                ArrayList<OschrenkItem> expected = insItems.stream()
                        .filter(item -> {
                            for (int keyIndex = 0; keyIndex < this.K_DIM; keyIndex++) {
                                if (item.keys[keyIndex] < minKeys[keyIndex]
                                    || item.keys[keyIndex] > maxKeys[keyIndex]) {
                                    return false;
                                }
                            }
                            return true;
                        })
                        .collect(Collectors.toCollection(ArrayList::new));

                assertEquals(expected.size(), found.size());
                assertTrue(found.containsAll(expected));

            }
        } catch (Exception ex) {
            System.out.println("[ERROR] " + ex.getMessage());
        }
    }
}
