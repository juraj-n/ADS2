package dataStructures.tree.kdTree;

import dataStructures.common.Comparable;
import org.junit.jupiter.api.Test;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class KDTreeTest {
    private final long seed = System.nanoTime();
    private final Random generator = new Random(seed);
    private final int K_DIM = 3;
    private final int WORLD_SIZE = 100;
    private final int FIND_PERC = 5;
    private final int NUMBER_OF_OPERATIONS = 100_000;
    @Test
    public void testInsertFind() {
        int insertCount = 0;
        int findCount = 0;

        KDTree<KDItem> tree = new KDTree<>(K_DIM);
        List<KDItem> insItems = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_OPERATIONS; i++) {
            if (generator.nextDouble(100) < 50) {
                insertCount++;
                this.insertRandomKeys(tree, insItems);
            } else {
                findCount++;
                this.findRandomRange(tree, insItems);
            }
        }
        System.out.println("[INSERT] " + insertCount);
        System.out.println("[-FIND-] " + findCount);
    }
    @Test
    public void testTreeContainsInserted() {
        KDTree<KDItem> tree = new KDTree<>(K_DIM);
        List<KDItem> insItems = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_OPERATIONS; i++) {
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
        for (int index = 0; index < K_DIM; index++) {
            double min = generator.nextDouble() * (this.WORLD_SIZE - side);
            double max = min + side;

            minKeys[index] = new KDDouble(min);
            maxKeys[index] = new KDDouble(max);
        }

        LinkedList<KDItem> found = tree.find(minKeys, maxKeys);

        if (this.FIND_PERC <= 1) {
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