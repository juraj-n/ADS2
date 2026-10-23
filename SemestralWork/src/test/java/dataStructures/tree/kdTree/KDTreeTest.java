package dataStructures.tree.kdTree;

import dataStructures.common.Comparable;
import dataStructures.tree.TestConstatns;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class KDTreeTest extends TestConstatns {
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

        KDTree<KDItem> tree = new KDTree<>(K_DIM);
        List<KDItem> insItems = new ArrayList<>();
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
    public void testTreeContainsInserted() {
        KDTree<KDItem> tree = new KDTree<>(K_DIM);
        List<KDItem> insItems = new ArrayList<>();
        for (int i = 0; i < this.NUMBER_OF_OPERATIONS; i++) {
            this.insertRandomKeys(tree, insItems);
        }

        Comparable[] minKeys = {new KDDouble(0), new KDDouble(0), new KDDouble(0)};
        Comparable[] maxKeys = {new KDDouble(100), new KDDouble(100), new KDDouble(100)};
        LinkedList<KDItem> found = tree.find(minKeys, maxKeys);

        assertEquals(insItems.size(), found.size(), "Seed: " + this.seed);
        assertTrue(found.containsAll(insItems), "Seed: " + this.seed);
    }
    private void insertRandomKeys(KDTree<KDItem> tree, List<KDItem> insItems) {
        KDDouble[] keys = new KDDouble[K_DIM];
        for (int keyIndex = 0; keyIndex < K_DIM; keyIndex++) {
            keys[keyIndex] = new KDDouble(generator.nextDouble(this.WORLD_SIZE));
        }
        KDDouble data = new KDDouble(generator.nextDouble());
        KDItem item = new KDItem(keys, data);

        if (tree.insert(keys, item)) {
            insItems.add(item);
        }
    }
    private void findRandomRange(KDTree<KDItem> tree, List<KDItem> insItems) {
        KDDouble[] minKeys = new KDDouble[K_DIM];
        KDDouble[] maxKeys = new KDDouble[K_DIM];

        double side = Math.pow(this.FIND_PERC / 100.0, 1.0 / this.K_DIM) * this.WORLD_SIZE;
        for (int index = 0; index < this.K_DIM; index++) {
            double min = generator.nextDouble() * (this.WORLD_SIZE - side);
            double max = min + side;

            minKeys[index] = new KDDouble(min);
            maxKeys[index] = new KDDouble(max);
        }

        LinkedList<KDItem> found = tree.find(minKeys, maxKeys);

        if (this.FIND_PERC <= 5) {
            LinkedList<KDItem> expected = insItems.stream()
                    .filter(item -> {
                        for (int keyIndex = 0; keyIndex < K_DIM; keyIndex++) {
                            if (item.keys[keyIndex].compare(minKeys[keyIndex]) < 0
                                    || item.keys[keyIndex].compare(maxKeys[keyIndex]) > 0) {
                                return false;
                            }
                        }
                        return true;
                    })
                    .collect(Collectors.toCollection(LinkedList::new));

            assertEquals(expected.size(), found.size(), "Seed: " + this.seed);
            assertTrue(found.containsAll(expected), "Seed: " + this.seed);
        }
    }
}